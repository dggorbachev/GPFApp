package com.singlelab.gpf.ui.my_profile.interactor

import com.singlelab.gpf.model.profile.Badge
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.Profile

interface MyProfileInteractor {
    suspend fun loadProfile(): Profile?

    suspend fun loadFriends(personUid: String): List<Person>?

    suspend fun updateImageProfile(imageStr: String, miniImageStr: String): String?

    suspend fun updatePushToken(token: String?)

    suspend fun clearDatabase()

    suspend fun removePushToken()

    suspend fun loadBadges(personUid: String): List<Badge>?

    suspend fun loadProfileFromCache(uid: String): Profile?

    suspend fun saveProfile(profile: Profile)

    suspend fun loadFriendsFromCache(): List<Person>?

    suspend fun saveFriends(friends: List<Person>)
}