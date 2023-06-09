package com.singlelab.gpf.ui.events

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.event.EventSummary
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EventsView : LoadingView, ErrorView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEvents(days: List<Pair<CalendarDay, List<EventSummary>>>, countInvites: Int = 0)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEventsOnCalendar(
        pastEvents: MutableList<EventSummary>,
        newInviteEvents: MutableList<EventSummary>,
        futureEvents: MutableList<EventSummary>,
        currentDay: CalendarDay? = null
    )

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCurrentDayOnPager(day: CalendarDay)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPromoReward(countEvents: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNewYearPromo(isShowed: Boolean)
}