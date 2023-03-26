package com.singlelab.gpf.ui.participants.interactor

import com.singlelab.gpf.model.event.Event
import com.singlelab.net.model.event.ParticipantRequest

interface ParticipantsInteractor {
    suspend fun approvePerson(participantRequest: ParticipantRequest): Event?

    suspend fun rejectPerson(personUid: String, eventUid: String): Event?
}