package com.singlelab.gpf.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.singlelab.gpf.database.entity.Chat

@Dao
internal abstract class ChatsDao : BaseDao<Chat> {
    @Query("select * from chats")
    internal abstract suspend fun all(): List<Chat>

    @Query("delete from chats")
    internal abstract suspend fun clear()
}