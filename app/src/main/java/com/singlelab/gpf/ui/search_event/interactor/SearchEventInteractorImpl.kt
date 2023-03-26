package com.singlelab.gpf.ui.search_event.interactor

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.model.event.EventSummary
import com.singlelab.net.model.event.SearchEventRequest
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.net.repositories.events.EventsRepository

class SearchEventInteractorImpl(private val repository: EventsRepository) : SearchEventInteractor,
    BaseInteractor(repository as BaseRepository) {
    override suspend fun search(searchEventRequest: SearchEventRequest) =
        repository.search(searchEventRequest)?.mapNotNull {
            EventSummary.fromResponse(it)
        }
}