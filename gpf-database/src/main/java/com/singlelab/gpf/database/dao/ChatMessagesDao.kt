package com.singlelab.gpf.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.singlelab.gpf.database.entity.ChatMessage

@Dao
internal abstract class ChatMessagesDao : BaseDao<ChatMessage> {
    @Query("select * from chat_messages where chatUid = :chatUid order by date")
    internal abstract suspend fun byChatUid(chatUid: String): List<ChatMessage>
}