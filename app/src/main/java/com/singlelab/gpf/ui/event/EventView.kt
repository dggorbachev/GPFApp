package com.singlelab.gpf.ui.event

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.event.Event
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EventView : LoadingView, ErrorView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEvent(event: Event)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun toProfile(personUid: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onRejectedEvent()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun toInviteFriends(eventUid: String, allParticipantIds: List<String>?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPromoReceiveButton(isShow: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessReport()
}