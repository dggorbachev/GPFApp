package com.singlelab.gpf.ui.events.interactor

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.database.repository.EventsSummaryRepository
import com.singlelab.gpf.model.event.EventSummary
import com.singlelab.gpf.model.promo.PromoReward
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.net.repositories.events.EventsRepository

class EventsInteractorImpl(
    private val repository: EventsRepository,
    private val localRepository: EventsSummaryRepository
) : EventsInteractor, BaseInteractor(repository as BaseRepository) {
    override suspend fun getEvents(): List<EventSummary>? {
        return repository.getEvents()?.mapNotNull {
            EventSummary.fromResponse(it)
        }
    }

    override suspend fun getEventsFromCache(): List<EventSummary>? {
        return localRepository.getEvents()?.mapNotNull { EventSummary.fromEntity(it) }
    }

    override suspend fun checkPromoReward(cityId: Int): PromoReward? {
        return PromoReward.fromResponse(repository.checkCityForPromoReward(cityId))
    }

    override suspend fun saveEventToCache(list: List<EventSummary>) {
        localRepository.clear()
        localRepository.insert(list.map { it.toEntity() })
    }
}