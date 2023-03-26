package com.singlelab.gpf.ui.chat.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.repository.RoomChatMessagesRepository
import com.singlelab.gpf.ui.chat.ChatPresenter
import com.singlelab.gpf.ui.chat.interactor.ChatInteractor
import com.singlelab.gpf.ui.chat.interactor.DefaultChatInteractor
import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.chat.ChatRepository
import com.singlelab.net.repositories.chat.DefaultChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.singlelab.gpf.database.repository.ChatMessagesRepository as LocalChatRepository
import com.singlelab.net.repositories.chat.ChatRepository as RemoteChatRepository

@InstallIn(ActivityComponent::class)
@Module
object ChatModule {
    @Provides
    fun providePresenter(
        interactor: ChatInteractor
    ): ChatPresenter = ChatPresenter(
        interactor = interactor,
        preferences = GPFApplication.preferences
    )

    @Provides
    fun provideInteractor(
        remoteRepository: RemoteChatRepository,
        localRepository: LocalChatRepository
    ): ChatInteractor = DefaultChatInteractor(
        remoteRepository = remoteRepository,
        localRepository = localRepository
    )

    @Provides
    fun provideRepository(
        apiUnit: ApiUnit
    ): ChatRepository = DefaultChatRepository(
        apiUnit = apiUnit
    )

    @Provides
    fun provideLocalRepository(
        database: GPFDatabase
    ): LocalChatRepository = RoomChatMessagesRepository(
        db = database
    )
}