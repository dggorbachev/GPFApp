package com.singlelab.gpf.ui.chats

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.new_features.firebase.ChatFirebase
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.chats.common.ChatItem
import com.singlelab.gpf.ui.chats.common.toUiEntities
import com.singlelab.gpf.ui.chats.interactor.ChatsInteractor
import kotlinx.coroutines.delay
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ChatsPresenter
@Inject
constructor(
    private val interactor: ChatsInteractor,
    preferences: Preferences?
) : BasePresenter<ChatsView>(
    preferences,
    interactor as BaseInteractor
) {

    companion object {

        var allChats = mutableListOf<ChatItem>()
    }

    override fun attachView(view: ChatsView?) {
        super.attachView(view)
        showChats()
    }

    private fun showChats() {
//        viewState.showLoading(true)
//        invokeSuspend {
//            try {
//                showChatsFromCache()
//                val remoteChats = interactor.remoteChats()
//                if (remoteChats != null) {
//                    interactor.saveChats(remoteChats.toDbEntities())
//                }
//                showLocalChats()
//            } catch (e: ApiException) {
//                showLocalChats()
//            }
//        }

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        lateinit var userDocuments: QuerySnapshot
        lateinit var lastMessageUser: UserFirebase
        lateinit var secondChatUser: UserFirebase
        val currentUserId = auth.currentUser!!.uid

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                userDocuments = result

                db.collection("chats")
                    .get()
                    .addOnSuccessListener { result ->

                        for (chatDocument in result) {

                            val chat = mapToObject(chatDocument.data, ChatFirebase::class)
                            var needToAdd = true

                            for (localChat in allChats) {
                                if (localChat.uid == chat.id) {
                                    needToAdd = false
                                }
                            }

                            if (needToAdd) {

                                if (chat.users.contains(currentUserId)) {

                                    if (chat.isGroup) {

                                        for (userDocument in userDocuments) {
                                            val user =
                                                mapToObject(userDocument.data, UserFirebase::class)

                                            if (user.id == chat.lastMessageUserId) {
                                                lastMessageUser = user
                                            }
                                        }

                                        allChats.add(
                                            ChatItem(
                                                uid = chat.id,
                                                image = chat.image,
                                                title = chat.title,
                                                isGroup = true,
                                                lastMessage = chat.lastMessageValue,
                                                lastMessagePersonUid = lastMessageUser.id,
                                                lastMessagePersonName = if (lastMessageUser.id == currentUserId) "Я" else lastMessageUser.login,
                                                isLastMessageImage = false,
                                                unreadMessagesCount = 0
                                            )
                                        )

                                    } else {

                                        for (userDocument in userDocuments) {
                                            val user =
                                                mapToObject(userDocument.data, UserFirebase::class)

                                            if (user.id == chat.lastMessageUserId) {
                                                lastMessageUser = user
                                            }

                                            if (chat.users.contains(user.id) && user.id != currentUserId) {
                                                secondChatUser = user
                                            }
                                        }

                                        allChats.add(
                                            ChatItem(
                                                uid = chat.id,
                                                image = secondChatUser.icon,
                                                title = secondChatUser.login,
                                                isGroup = false,
                                                lastMessage = chat.lastMessageValue,
                                                lastMessagePersonUid = lastMessageUser.id,
                                                lastMessagePersonName = if (lastMessageUser.id == currentUserId) "Я" else lastMessageUser.login,
                                                isLastMessageImage = false,
                                                unreadMessagesCount = 0
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        viewState.showChats(allChats)
                    }
            }

    }

    private suspend fun showChatsFromCache() {
        val chats = interactor.localChats().toUiEntities()
        delay(Const.MIN_DELAY_FOR_TRANSITION)
        runOnMainThread {
            if (chats.isNotEmpty()) {
                viewState.showLoading(false)
                viewState.showChats(chats)
            }
        }
    }

    private suspend fun showLocalChats() {
        val chats = interactor.localChats().toUiEntities()
        runOnMainThread {
            viewState.showLoading(false)
            if (chats.isEmpty()) {
                viewState.showEmptyChats()
            } else {
                viewState.showChats(chats)
            }
        }
    }
}