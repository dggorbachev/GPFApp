package com.singlelab.gpf.ui.event.interactor

import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.promo.PromoReward
import com.singlelab.net.model.event.ParticipantRequest
import com.singlelab.net.model.event.UpdateEventRequest

interface EventInteractor {
    suspend fun getEvent(uid: String): Event?

    suspend fun acceptEvent(participantRequest: ParticipantRequest): Event?

    suspend fun joinEvent(participantRequest: ParticipantRequest): Event?

    suspend fun rejectEvent(personUid: String, eventUid: String)

    suspend fun updateEvent(request: UpdateEventRequest): Event?

    suspend fun checkPromoReward(cityId: Int): PromoReward?

    suspend fun sendReport(uid: String, reasonReport: String)

    suspend fun getEventFromCache(uid: String): Event?
}