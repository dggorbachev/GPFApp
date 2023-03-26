package com.singlelab.gpf.ui.chats

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
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

        var allChats = mutableListOf<ChatItem>(
            ChatItem(
                uid = "1",
                title = "Вячеслав",
                image = "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                isGroup = false,
                lastMessage = "Проиграли...",
                lastMessagePersonUid = "1",
                lastMessagePersonName = "Вячеслав",
                isLastMessageImage = false,
                unreadMessagesCount = 1
            ),
            ChatItem(
                uid = "2",
                title = "Александр",
                image = "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                isGroup = false,
                lastMessage = "Я на подобных картах только сливаю ело...",
                lastMessagePersonUid = "2",
                lastMessagePersonName = "Александр",
                isLastMessageImage = false,
                unreadMessagesCount = 3
            ),
            ChatItem(
                uid = "3",
                title = "Чатик 5% винрейт",
                image = "https://i.ytimg.com/vi/kGTfwM5wPSU/maxresdefault.jpg",
                isGroup = true,
                lastMessage = "парни, всем привет. " +
                        "посмотрел про совместимость трекера с гит платформами. Ситуация такая: он поддерживает гитхаб, гитлаб и бакет. С последним нужно проводить определенные махинации, а с остальными должно быть все ок.",
                lastMessagePersonUid = "1",
                lastMessagePersonName = "Вячеслав",
                isLastMessageImage = false,
                unreadMessagesCount = 1
            )
        )
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
        viewState.showChats(allChats)

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