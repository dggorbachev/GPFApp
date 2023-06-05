package com.singlelab.gpf.ui.chat

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.ui.chat.common.ChatMessageItem
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatView : LoadingView, ErrorView {
    fun showChat(messages: List<ChatMessageItem>, page: Int)
    fun showEmptyChat()
    fun showNewMessage(message: ChatMessageItem)
    fun enableMessageSending(isEnabled: Boolean)
    fun showAddMember(isMuted: Boolean)
    fun showTitle(title: String?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToEvent(eventUid: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToPerson(personUid: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMuteDialog(isMuted: Boolean)

}