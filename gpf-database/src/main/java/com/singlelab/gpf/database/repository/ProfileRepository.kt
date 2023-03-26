package com.singlelab.gpf.database.repository

import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.entity.Person
import com.singlelab.gpf.database.entity.Profile

interface ProfileRepository {
    suspend fun get(uid: String): Profile?
    suspend fun insert(profile: Profile)
    suspend fun clear()
    suspend fun getFriends(): List<Person> = listOf()
    suspend fun clearFriends()
    suspend fun insertFriends(friends: List<Person>)
}

class RoomProfileRepository(db: GPFDatabase) : ProfileRepository {
    private val dao = db.profileDao()
    private val friendsDao = db.friendsDao()

    override suspend fun get(uid: String) = dao.getProfile(uid)

    override suspend fun insert(profile: Profile) {
        dao.insertOrReplace(profile)
    }

    override suspend fun clear() {
        dao.clear()
    }

    override suspend fun getFriends(): List<Person> = friendsDao.getFriends()

    override suspend fun insertFriends(friends: List<Person>) {
        friendsDao.insertOrReplace(friends)
    }

    override suspend fun clearFriends() {
        friendsDao.clear()
    }
}