package com.singlelab.gpf.ui.event.interactor

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.database.repository.EventsSummaryRepository
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.promo.PromoReward
import com.singlelab.net.model.event.ParticipantRequest
import com.singlelab.net.model.event.ReportEventRequest
import com.singlelab.net.model.event.UpdateEventRequest
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.net.repositories.events.EventsRepository

class EventInteractorImpl(
    private val repository: EventsRepository,
    private val localRepository: EventsSummaryRepository
) : EventInteractor, BaseInteractor(repository as BaseRepository) {

    override suspend fun getEvent(uid: String): Event? {
        return Event.fromResponse(repository.getEvent(uid))
    }

    override suspend fun acceptEvent(participantRequest: ParticipantRequest): Event? {
        return Event.fromResponse(repository.updateParticipants(participantRequest))
    }

    override suspend fun joinEvent(participantRequest: ParticipantRequest): Event? {
        return Event.fromResponse(repository.addParticipants(participantRequest))
    }

    override suspend fun rejectEvent(personUid: String, eventUid: String) {
        repository.removeParticipants(personUid, eventUid)
    }

    override suspend fun updateEvent(request: UpdateEventRequest): Event? {
        return Event.fromResponse(repository.updateEvent(request))
    }

    override suspend fun checkPromoReward(cityId: Int): PromoReward? {
        return PromoReward.fromResponse(repository.checkCityForPromoReward(cityId))
    }

    override suspend fun sendReport(uid: String, reasonReport: String) {
        repository.sendReport(ReportEventRequest(uid, reasonReport))
    }

    override suspend fun getEventFromCache(uid: String): Event? {
        return Event.fromEventSummaryEntity(localRepository.getEvent(uid))
    }
}