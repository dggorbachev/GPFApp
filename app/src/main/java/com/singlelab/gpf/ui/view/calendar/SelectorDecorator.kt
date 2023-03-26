package com.singlelab.gpf.ui.view.calendar

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.singlelab.gpf.R

class SelectorDecorator(private val context: Context) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        context.getDrawable(R.color.transparent)?.let {
            view.setSelectionDrawable(it)
        }
    }
}
