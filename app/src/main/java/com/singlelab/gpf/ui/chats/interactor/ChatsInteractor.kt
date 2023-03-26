package com.singlelab.gpf.ui.chats.interactor

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.database.entity.Chat
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.model.chat.ChatResponse
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.gpf.database.repository.ChatsRepository as LocalChatsRepository
import com.singlelab.net.repositories.chats.ChatsRepository as RemoteChatsRepository

interface ChatsInteractor {
    @Throws(ApiException::class)
    suspend fun remoteChats(): List<ChatResponse>?

    suspend fun saveChats(chats: List<Chat>)
    suspend fun localChats(): List<Chat>
}

class DefaultChatsInteractor(
    private val remoteRepository: RemoteChatsRepository,
    private val localRepository: LocalChatsRepository
) : BaseInteractor(
    remoteRepository as BaseRepository
), ChatsInteractor {

    override suspend fun remoteChats() =
        remoteRepository.loadChats()

    override suspend fun saveChats(chats: List<Chat>) {
        localRepository.clear()
        localRepository.insert(chats)
    }

    override suspend fun localChats() =
        localRepository.all()
}