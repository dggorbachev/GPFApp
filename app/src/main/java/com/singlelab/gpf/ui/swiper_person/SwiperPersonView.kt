package com.singlelab.gpf.ui.swiper_person

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.profile.Person
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SwiperPersonView : LoadingView, ErrorView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPerson(person: Person)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun toAcceptedPerson(person: Person, eventUid: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptySwipes()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessReport()
}