package com.singlelab.gpf.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.singlelab.gpf.database.entity.Profile

@Dao
internal abstract class ProfileDao : BaseDao<Profile> {
    @Query("select * from profile where personUid=:uid limit 1")
    internal abstract suspend fun getProfile(uid: String): Profile?

    @Query("delete from profile")
    internal abstract suspend fun clear()
}