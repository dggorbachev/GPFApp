package com.singlelab.gpf.ui.chats

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.ui.chats.common.ChatItem
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatsView : LoadingView, ErrorView {
    fun showChats(chats: List<ChatItem>)
    fun showEmptyChats()
}