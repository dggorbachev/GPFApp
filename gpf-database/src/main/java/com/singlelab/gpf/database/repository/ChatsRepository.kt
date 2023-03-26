package com.singlelab.gpf.database.repository

import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.entity.Chat

interface ChatsRepository {
    suspend fun insert(chats: Collection<Chat>)
    suspend fun insert(chat: Chat)
    suspend fun all(): List<Chat>
    suspend fun clear()
}

class RoomChatsRepository(db: GPFDatabase) : ChatsRepository {
    private val dao = db.chatsDao()

    override suspend fun insert(chats: Collection<Chat>) =
        dao.insertOrReplace(chats)

    override suspend fun insert(chat: Chat) =
        dao.insertOrReplace(chat)

    override suspend fun all() =
        dao.all()

    override suspend fun clear() =
        dao.clear()
}