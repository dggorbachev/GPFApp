package com.singlelab.gpf.ui.chat

import android.graphics.Bitmap
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const.DATE_FORMAT_TIME_ZONE
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.chat.common.*
import com.singlelab.gpf.ui.chat.interactor.ChatInteractor
import com.singlelab.gpf.ui.chats.ChatsPresenter
import com.singlelab.gpf.util.parseToString
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.exceptions.TimeoutException
import com.singlelab.net.model.chat.ChatMessageResponse
import moxy.InjectViewState
import java.util.*
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

        var messages1 = mutableListOf<ChatMessageItem>(
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Я на сегодня все, спасибо за игры!",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T12:22:10.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Уже и не 5% винрейт после последней игры",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T12:23:00.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Ахахахахахаха, получается так",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T12:23:10.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "А я в итоге не нашел нормальный спот у Харетона",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T12:23:30.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Ну хз, мне зашел верхний левый",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T12:24:10.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Дань, у меня кд там 0.3...",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T15:20:15.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Это место пугает меня...",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T16:32:01.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Никуда не денешься, лучше лута на карте нет",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T16:42:01.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Проиграли...",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-23T16:42:11.000",
                images = listOf()
            )
        )

        var messages2 = mutableListOf<ChatMessageItem>(
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Привет, ты играл в рейтинговые игры в RS6?",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-25T20:29:51.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Да, играл несколько раз. Играю за Sledge.",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-25T20:31:00.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Как твои результаты? Удалось поднять свой ранг?",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-26T15:24:23.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Немного поднял ранг, но были и поражения. Иногда команды не очень слаживаются.",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-26T15:34:43.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Понимаю. Я тоже играл в рейтинговые игры, но мне сложно играть на высоких уровнях.",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-26T15:44:44.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Да, это действительно сложно. Нужно иметь не только хорошие навыки игры, но и командную работу.",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-26T15:46:46.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "А какая карта в рейтинговых играх тебе нравится больше всего?",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-27T15:46:46.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Я предпочитаю играть на карте Клубный дом. Она небольшая, но у неё хорошая динамика.",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-27T15:50:50.000",
                images = listOf()
            ),

            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Понятно. Я тоже люблю эту карту, но мне больше нравится играть на более больших картах.",
                type = ChatMessageItem.Type.OUTGOING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-27T15:53:23.000",
                images = listOf()
            ),
            PrivateChatMessageItem(
                uid = ChatMessageItem.PENDING_MESSAGE_UID,
                text = "Я на подобных картах только сливаю ело...",
                type = ChatMessageItem.Type.INCOMING,
                status = ChatMessageItem.Status.SYNCED,
                date = "2023-03-27T15:54:45.000",
                images = listOf()
            )
        )

        val messages3 = mutableListOf(
            GroupChatMessageItem(
                "2",
                "По следам наших последних созвонов с Ксюшей: \n" +
                        "1) Все текущие макеты залиты в фигму, ссылка на которую лежит в чатике для дизайна. \n" +
                        "2) Ксюша предложила интересный вариант удаления игр и платформ. Мы делаем это не на вкладке редактирования, а на вкладке профиля следующим образом: у каждой игры и платформы есть крестик.",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T12:35:12.000",
                "2",
                personPhoto = "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                personName = "Александр"
            ),
            GroupChatMessageItem(
                "2",
                "Как там, кстати, дела с архитектурой?",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T12:35:30.000",
                "2",
                personPhoto = "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                personName = "Александр"
            ),
            GroupChatMessageItem(
                "2",
                "Так",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:40:20.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "По архитектуре",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:40:23.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "Я набросал основу сделал шаблон",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:40:30.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "С Сашей на неделе увидимся",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:40:59.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "И глянем, сколько экран займет",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:41:07.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "А можно посмотреть?",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:45:56.000",
                "2",
                personPhoto = "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                personName = "Александр"
            ),
            GroupChatMessageItem(
                "2",
                "Гита пока нет",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:47:02.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "Сделаем его на днях!",
                ChatMessageItem.Type.OUTGOING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-24T15:47:54.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "1",
                "давайте созвонимся, мы не обсуждали, что будем делать с карточками кандидатов. Думаю, сейчас самое время. Плюс есть ещё пара вопросов, которые надо обмозговать. Завтра часов в 20-21 сможете?",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-25T16:23:12.000",
                "2",
                personPhoto = "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                personName = "Александр"
            ),
            GroupChatMessageItem(
                "2",
                "Ок, я готов после 8 вечера",
                ChatMessageItem.Type.OUTGOING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-25T17:45:45.000",
                "1",
                personPhoto = "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                personName = "Александр"
            ),
            GroupChatMessageItem(
                "2",
                "Подключусь к 20:30",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-25T18:32:53.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "2",
                "вы не обсуждали с остальными, чтобы добавить например 2-3 основные функции так или сложно при реализации вам? можно было бы таким способом сделать закрепить/ добавить в команду/ выкл уведомления\n" +
                        "удаление сюда точно не стоит, вдруг нечайно тапнут, а так эти функции например \n" +
                        "не знаю лишнее или нет, но как удобство для пользователя мне кажется плюс",
                ChatMessageItem.Type.OUTGOING,
                listOf("https://i.imgur.com/uQaGGB2.png"),
                ChatMessageItem.Status.SYNCED,
                "2023-03-26T23:54:53.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            ),
            GroupChatMessageItem(
                "3",
                "парни, всем привет\n" +
                        "посмотрел про совместимость трекера с гит платформами. Ситуация такая: он поддерживает гитхаб, гитлаб и бакет. С последним нужно проводить определенные махинации, а с остальными должно быть все ок. Дальше чекнул оф сайт гитлаба, есть бесплатная подписка.",
                ChatMessageItem.Type.INCOMING,
                listOf(),
                ChatMessageItem.Status.SYNCED,
                "2023-03-27T02:53:56.000",
                "1",
                personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                personName = "Вячеслав"
            )

        )

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

        val temp = Calendar.getInstance()
        temp.timeZone = TimeZone.getTimeZone("GMT")
        val date = temp.parseToString(DATE_FORMAT_TIME_ZONE)


        val message = PrivateChatMessageItem(
            uid = ChatMessageItem.PENDING_MESSAGE_UID,
            text = messageText,
            type = ChatMessageItem.Type.OUTGOING,
            status = ChatMessageItem.Status.SYNCED,
            date = date,
            images = listOf()
        )

        val groupMessage = GroupChatMessageItem(
            "2",
            messageText,
            ChatMessageItem.Type.OUTGOING,
            listOf(),
            ChatMessageItem.Status.SYNCED,
            date,
            "1",
            personPhoto = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
            personName = "Вячеслав"
        )

        when (chatUid) {
            "1" -> {
                messages1.add(message)
                ChatsPresenter.allChats[0].lastMessage = messageText
                ChatsPresenter.allChats[0].lastMessagePersonName = "Я"

            }
            "2" -> {
                messages2.add(message)
                ChatsPresenter.allChats[1].lastMessage = messageText
                ChatsPresenter.allChats[1].lastMessagePersonName = "Я"

            }
            "3" -> {
                messages3.add(groupMessage)
                ChatsPresenter.allChats[2].lastMessage = messageText
                ChatsPresenter.allChats[2].lastMessagePersonName = "Я"

            }
            else -> {}
        }
        viewState.showNewMessage(message)
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
        runOnMainThread {
            when (chatUid) {
                "1" -> {
                    viewState.showChat(messages1, page)
                    ChatsPresenter.allChats[0].unreadMessagesCount = 0
                }
                "2" -> {
                    viewState.showChat(messages2, page)
                    ChatsPresenter.allChats[1].unreadMessagesCount = 0

                }
                "3" -> {
                    viewState.showChat(messages3, page)
                    ChatsPresenter.allChats[2].unreadMessagesCount = 0

                }
                else -> {}
            }
        }
    }

    private suspend fun syncMessages() {
        try {
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
}