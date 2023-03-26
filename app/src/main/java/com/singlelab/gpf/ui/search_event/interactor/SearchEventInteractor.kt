package com.singlelab.gpf.ui.search_event.interactor

import com.singlelab.gpf.model.event.EventSummary
import com.singlelab.net.model.event.SearchEventRequest

interface SearchEventInteractor {
    suspend fun search(searchEventRequest: SearchEventRequest): List<EventSummary>?
}