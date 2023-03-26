package com.singlelab.gpf.ui.person.interactor

import com.singlelab.gpf.model.profile.Profile

interface PersonInteractor {
    suspend fun loadPerson(personUid: String): Profile?

    suspend fun addToFriends(personUid: String)

    suspend fun removeFromFriends(personUid: String)

    suspend fun sendReport(personUid: String, reasonReport: String)
}