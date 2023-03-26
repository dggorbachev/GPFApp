package com.singlelab.gpf.ui.events.interactor

import com.singlelab.gpf.model.event.EventSummary
import com.singlelab.gpf.model.promo.PromoReward

interface EventsInteractor {
    suspend fun getEvents(): List<EventSummary>?

    suspend fun getEventsFromCache(): List<EventSummary>?

    suspend fun checkPromoReward(cityId: Int): PromoReward?

    suspend fun saveEventToCache(list: List<EventSummary>)
}