package com.singlelab.gpf.ui.chat

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const.DATE_FORMAT_TIME_ZONE
import com.singlelab.gpf.new_features.firebase.ChatFirebase
import com.singlelab.gpf.new_features.firebase.MessageFirebase
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.chat.common.*
import com.singlelab.gpf.ui.chat.interactor.ChatInteractor
import com.singlelab.gpf.ui.chats.ChatsPresenter
import com.singlelab.gpf.util.formatToUTC
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.exceptions.TimeoutException
import com.singlelab.net.model.chat.ChatMessageResponse
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ChatPresenter
@Inject
constructor(
    private val interactor: ChatInteractor,
    preferences: Preferences?
) : BasePresenter<ChatView>(
    preferences,
    interactor as BaseInteractor
) {

    companion object {

        lateinit var currentUserId: String

        lateinit var selectedChat: ChatFirebase

        lateinit var chatNotFound: String

        lateinit var personIdWeCameFrom: String

        var chatUsers = mutableListOf<UserFirebase>()

        var privateMessagesToShow = mutableListOf<PrivateChatMessageItem>()

        var groupMessagesToShow = mutableListOf<GroupChatMessageItem>()

        var chatUid: String? = ""
    }

    var page: Int = 1
    val chatSettings = ChatSettings()

    private var isLoading: Boolean = false
    private var hasOldMessages: Boolean = true

    fun showChat(type: ChatOpeningInvocationType? = null, page: Int = 1) {
//        if (page == 1) {
//            Analytics.logOpenChat()
//            viewState.showLoading(false)
//        }
//        invokeSuspend {
//            try {
//                isLoading = true
//                val chatResponse = when (type) {
//                    is ChatOpeningInvocationType.Person -> interactor.loadPersonChat(
//                        type.personUid,
//                        page
//                    )
//                    is ChatOpeningInvocationType.Common -> interactor.loadChatByUid(
//                        type.chatUid,
//                        page
//                    )
//                    else -> null
//                }
//                if (chatResponse != null) {
//                    chatSettings.chatType = type
//                    chatSettings.chatUid = chatResponse.chatUid
//                    chatSettings.eventUid = chatResponse.eventUid
//                    chatSettings.personUid = chatResponse.personUid
//                    chatSettings.isMuted = chatResponse.isMuted
//                    chatSettings.setLastMessageUid(chatResponse.messages)
//                    runOnMainThread {
//                        if (chatResponse.unreadMessagesCount > 0) {
//                            updateNotifications()
//                        }
//                        viewState.showTitle(chatSettings.chatType?.title)
//                        viewState.showMute(chatSettings.isMuted)
//                    }
//                    isLoading = false
//                    if (chatResponse.messages.isNullOrEmpty()) {
//                        hasOldMessages = false
//                    } else {
//                        saveChatMessages(chatResponse.messages)
//                    }
//                    showLocalMessages()
//                    syncMessages()
//                } else {
//                    runOnMainThread { viewState.showError("Ошибка при загрузке чата") }
//                }
//            } catch (e: Exception) {
//                isLoading = false
//                runOnMainThread { viewState.showError("Сообщения могут быть неактуальны") }
//                showLocalMessages()
//            }
//        }
        invokeSuspend {
            showLocalMessages()

            var title = when (chatUid) {
                "1" -> {
                    "Вячеслав"
                }
                "2" -> {
                    "Александр"
                }
                "3" -> {
                    "Чатик 5% винрейт"
                }
                else -> {
                    "Чат"
                }
            }

            viewState.showTitle(title)
        }
    }

    fun sendMessage(messageText: String, images: List<Bitmap>) {
//        invokeSuspend {
//            try {
//                runOnMainThread { viewState.enableMessageSending(false) }
//                val chatUid = chatSettings.chatUid
//                if (chatUid != null) {
//                    val compressedImages = images.map { it.resize().toBase64() }
//                    val newMessage = interactor.sendMessage(
//                        ChatMessageRequest(
//                            chatUid,
//                            messageText,
//                            compressedImages
//                        )
//                    )
//                    if (newMessage != null) {
//                        chatSettings.setLastMessageUid(newMessage)
//                        val messageEntity = newMessage.toDbEntity(chatUid)
//                        if (messageEntity != null) {
//                            interactor.saveChatMessage(messageEntity)
//                            val currentPersonUid = AuthData.uid
//                            val chatType = chatSettings.chatType
//                            if (chatType != null && currentPersonUid != null) {
//                                val message =
//                                    messageEntity.toUiEntity(chatType.isGroup, currentPersonUid)
//                                Analytics.logSendMessage()
//                                runOnMainThread {
//                                    viewState.showNewMessage(message)
//                                }
//                            }
//                        }
//                    }
//                }
//                runOnMainThread { viewState.enableMessageSending(true) }
//            } catch (e: ApiException) {
//                runOnMainThread {
//                    viewState.showError(e.message)
//                    viewState.enableMessageSending(true)
//                }xzl
//            }
//        }

        val outgoingMessage = MessageFirebase(
            chatId = chatUid!!,
            id = getRandomString(),
            images = listOf(""),
            message = messageText,
            senderId = currentUserId,
            messageDate = Timestamp.now(),
            isAnyAttachments = false
        )

        val messageDocData = hashMapOf(
            "chatId" to outgoingMessage.chatId,
            "id" to outgoingMessage.id,
            "images" to outgoingMessage.images,
            "message" to outgoingMessage.message,
            "senderId" to outgoingMessage.senderId,
            "messageDate" to outgoingMessage.messageDate,
            "isAnyAttachments" to outgoingMessage.isAnyAttachments
        )

        val chatDocData = hashMapOf(
            "id" to selectedChat.id,
            "image" to selectedChat.image,
            "isGroup" to selectedChat.isGroup,
            "isLastMessageImage" to false,
            "lastMessageUserId" to currentUserId,
            "lastMessageValue" to messageText,
            "title" to selectedChat.title,
            "users" to selectedChat.users
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("messages").document(outgoingMessage.id)
            .set(messageDocData).addOnSuccessListener {
                db.collection("chats").document(selectedChat.id)
                    .set(chatDocData)
            }



        if (selectedChat.isGroup) {
            groupMessagesToShow.add(outgoingMessage.toGroupChatMessage())
            viewState.showNewMessage(outgoingMessage.toGroupChatMessage())

        } else {
            privateMessagesToShow.add(outgoingMessage.toPrivateChatMessage())
            viewState.showNewMessage(outgoingMessage.toPrivateChatMessage())
        }

        if (chatNotFound == "false") {
            ChatsPresenter.allChats.first { it.uid == selectedChat.id }.apply {
                lastMessage = messageText
                lastMessagePersonName = "Я"
            }
        }
    }

    fun onChatTitleClick() {
        if (chatSettings.chatType != null && chatSettings.chatType!!.isGroup) {
            chatSettings.eventUid?.let {
                viewState.navigateToEvent(it)
            }
        } else {
            chatSettings.personUid?.let {
                viewState.navigateToPerson(it)
            }
        }
    }

    fun onChatMuteClick() {
        viewState.showMuteDialog(chatSettings.isMuted)
    }

    fun muteChat() {
//        chatSettings.chatUid?.let { uid ->
//            viewState.showLoading(false)
//            invokeSuspend {
//                try {
//                    interactor.muteChat(uid, !chatSettings.isMuted)
//                    chatSettings.isMuted = !chatSettings.isMuted
//                    runOnMainThread {
//                        viewState.showLoading(false)
//                        viewState.showMute(chatSettings.isMuted)
//                    }
//                } catch (e: ApiException) {
//                    runOnMainThread {
//                        viewState.showLoading(false)
//                        viewState.showError(e.message)
//                    }
//                }
//            }
//        }
        invokeSuspend {

        }
    }

    fun isNeedLoading() = !isLoading && hasOldMessages

    private suspend fun showLocalMessages() {
//        //todo если нет интернета, эти параметры будут null, подумать над тем, как это реализовать
//        val chatUid = chatSettings.chatUid
//        val chatType = chatSettings.chatType
//        val currentPersonUid = AuthData.uid
//        val messages = if (chatUid != null && chatType != null && currentPersonUid != null) {
//            interactor.byChatUid(chatUid).toUiEntities(chatType.isGroup, currentPersonUid)
//        } else {
//            emptyList()
//        }
//        runOnMainThread {
//            viewState.showLoading(false)
//            if (messages.isEmpty()) {
//                viewState.showEmptyChat()
//            } else {
//                viewState.showChat(messages, page)
//            }
//        }

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        currentUserId = auth.currentUser!!.uid
        var chatMessages = mutableListOf<MessageFirebase>()
        chatNotFound = "true"

        db.collection("chats")
            .get()
            .addOnSuccessListener { result ->
                for (chatDoc in result) {

                    val chat = mapToObject(chatDoc.data, ChatFirebase::class)

                    if (chat.id == chatUid) {
                        selectedChat = chat
                        chatNotFound = "false"
                    }
                }

                if (chatNotFound == "true") {
                    val generatedChatId = getRandomString(20)
                    selectedChat = ChatFirebase(
                        id = generatedChatId,
                        image = "",
                        isGroup = false,
                        isLastMessageImage = false,
                        lastMessageUserId = currentUserId,
                        lastMessageValue = "",
                        title = "",
                        users = listOf(currentUserId, personIdWeCameFrom)
                    )

                    Log.d("ChatUid1", generatedChatId)
                    chatUid = generatedChatId

                    privateMessagesToShow = mutableListOf<PrivateChatMessageItem>()

                    runOnMainThread {
                        viewState.showChat(privateMessagesToShow, page)
                    }
                } else {

                    db.collection("users")
                        .get()
                        .addOnSuccessListener { userDocuments ->
                            for (userDocument in userDocuments) {
                                val user =
                                    mapToObject(userDocument.data, UserFirebase::class)

                                if (selectedChat.users.contains(user.id)) {
                                    chatUsers.add(user)
                                }
                            }

                            db.collection("messages")
                                .get()
                                .addOnSuccessListener { messageDocs ->
                                    for (messageDoc in messageDocs) {
                                        val message =
                                            mapToObject(messageDoc.data, MessageFirebase::class)

                                        if (message.chatId == selectedChat.id) {
                                            chatMessages.add(message)
                                        }
                                    }

                                    chatMessages.sortBy { it.messageDate.toDate() }

                                    if (selectedChat.isGroup) {

                                        groupMessagesToShow = chatMessages.toGroupChatMessageItems()

                                        runOnMainThread {
                                            viewState.showChat(groupMessagesToShow, page)
                                        }
                                    } else {

                                        privateMessagesToShow =
                                            chatMessages.toPrivateChatMessageItems()

                                        runOnMainThread {
                                            viewState.showChat(privateMessagesToShow, page)
                                        }
                                    }

                                    runOnMainThread {


                                        ChatsPresenter.allChats.firstOrNull() { it.uid == selectedChat.id }
                                            ?.apply {
                                                unreadMessagesCount = 0
                                            }
                                    }

                                }
                        }
                }
            }
    }

    private suspend fun syncMessages() {
        try {
            Log.d("ChatUid2", chatSettings.chatUid)
            val chatUid = chatSettings.chatUid
            val chatLastMessage = chatSettings.lastMessageUid
            if (chatUid != null) {
                val messagesResponse = interactor.loadNewMessages(chatUid, chatLastMessage)
                chatSettings.setLastMessageUid(messagesResponse)
                saveChatMessages(messagesResponse)
                showLocalMessages()
            }
            syncMessages()
        } catch (e: ApiException) {
            if (e is TimeoutException) {
                syncMessages()
            } else {
                runOnMainThread { viewState.showError(e.message) }
            }
        }
    }

    private suspend fun saveChatMessages(chatResponseMessages: List<ChatMessageResponse>?) {
        if (chatResponseMessages != null) {
            interactor.saveChatMessages(chatResponseMessages.toDbEntities(chatSettings.chatUid))
        }
    }

    data class ChatSettings(
        private var _lastMessageUid: String? = null,
        var chatType: ChatOpeningInvocationType? = null,
        var chatUid: String? = null,
        var eventUid: String? = null,
        var personUid: String? = null,
        var isMuted: Boolean = false
    ) {
        val lastMessageUid
            get() = _lastMessageUid

        fun setLastMessageUid(messagesResponse: List<ChatMessageResponse>?) {
            if (!messagesResponse.isNullOrEmpty()) {
                messagesResponse.last().messageUid?.let { lastMessageUid ->
                    _lastMessageUid = lastMessageUid
                }
            }
        }

        fun setLastMessageUid(messageResponse: ChatMessageResponse) {
            _lastMessageUid = messageResponse.messageUid
        }
    }

    private fun MutableList<MessageFirebase>.toPrivateChatMessageItems(): MutableList<PrivateChatMessageItem> {

        return map {
            PrivateChatMessageItem(
                uid = it.id,
                text = it.message,
                date = it.messageDate.toDate().formatToUTC(DATE_FORMAT_TIME_ZONE),
                images = if (it.isAnyAttachments) it.images else listOf<String>(),
                type = if (currentUserId == it.senderId) ChatMessageItem.Type.OUTGOING else ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED
            )
        }.toMutableList()

    }

    private fun MutableList<MessageFirebase>.toGroupChatMessageItems(): MutableList<GroupChatMessageItem> {

        return map { message ->

            lateinit var sender: UserFirebase

            for (user in chatUsers) {
                if (user.id == message.senderId) {
                    sender = user
                }
            }

            GroupChatMessageItem(
                uid = message.id,
                text = message.message,
                date = message.messageDate.toDate().formatToUTC(DATE_FORMAT_TIME_ZONE),
                images = if (message.isAnyAttachments) message.images else listOf<String>(),
                type = if (currentUserId == message.senderId) ChatMessageItem.Type.OUTGOING else ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                personName = sender.login,
                personPhoto = sender.icon,
                personUid = sender.id
            )
        }.toMutableList()

    }

    private fun MessageFirebase.toGroupChatMessage(): GroupChatMessageItem {

        lateinit var sender: UserFirebase

        for (user in chatUsers) {
            if (user.id == this.senderId) {
                sender = user
            }
        }

        return GroupChatMessageItem(
            uid = this.id,
            text = this.message,
            date = this.messageDate.toDate().formatToUTC(DATE_FORMAT_TIME_ZONE),
            images = if (this.isAnyAttachments) this.images else listOf<String>(),
            type = if (currentUserId == this.senderId) ChatMessageItem.Type.OUTGOING else ChatMessageItem.Type.INCOMING,
            status = ChatMessageItem.Status.SYNCED,
            personName = sender.login,
            personPhoto = sender.icon,
            personUid = sender.id
        )
    }

    private fun MessageFirebase.toPrivateChatMessage(): PrivateChatMessageItem {

        lateinit var sender: UserFirebase

        for (user in chatUsers) {
            if (user.id == this.senderId) {
                sender = user
            }
        }

        return PrivateChatMessageItem(
            uid = this.id,
            text = this.message,
            date = this.messageDate.toDate().formatToUTC(DATE_FORMAT_TIME_ZONE),
            images = if (this.isAnyAttachments) this.images else listOf<String>(),
            type = if (currentUserId == this.senderId) ChatMessageItem.Type.OUTGOING else ChatMessageItem.Type.INCOMING,
            status = ChatMessageItem.Status.SYNCED
        )
    }

    fun getRandomString(length: Int = 20): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}
