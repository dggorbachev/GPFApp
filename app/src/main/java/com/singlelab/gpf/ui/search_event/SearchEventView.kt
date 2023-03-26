package com.singlelab.gpf.ui.search_event

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.event.EventSummary
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SearchEventView : LoadingView, ErrorView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSearchResults(events: List<EventSummary>?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyQuery()
}