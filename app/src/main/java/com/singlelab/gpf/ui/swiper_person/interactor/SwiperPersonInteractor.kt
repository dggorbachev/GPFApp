package com.singlelab.gpf.ui.swiper_person.interactor

import com.singlelab.gpf.model.profile.Person
import com.singlelab.net.model.event.ParticipantRequest
import com.singlelab.net.model.person.RandomPersonRequest

interface SwiperPersonInteractor {
    suspend fun getRandomPerson(randomPersonRequest: RandomPersonRequest): Person?

    suspend fun invitePerson(participantRequest: ParticipantRequest)

    suspend fun rejectPerson(eventUid: String, personUid: String)

    suspend fun sendReport(uid: String, reasonReport: String)
}