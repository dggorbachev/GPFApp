package com.singlelab.gpf.ui.chats.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.repository.RoomChatsRepository
import com.singlelab.gpf.ui.chats.ChatsPresenter
import com.singlelab.gpf.ui.chats.interactor.ChatsInteractor
import com.singlelab.gpf.ui.chats.interactor.DefaultChatsInteractor
import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.chats.DefaultChatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.singlelab.gpf.database.repository.ChatsRepository as LocalChatsRepository
import com.singlelab.net.repositories.chats.ChatsRepository as RemoteChatsRepository

@InstallIn(ActivityComponent::class)
@Module
object ChatsModule {
    @Provides
    fun providePresenter(
        interactor: ChatsInteractor
    ): ChatsPresenter = ChatsPresenter(
        interactor = interactor,
        preferences = GPFApplication.preferences
    )

    @Provides
    fun provideInteractor(
        remoteRepository: RemoteChatsRepository,
        localRepository: LocalChatsRepository
    ): ChatsInteractor = DefaultChatsInteractor(
        remoteRepository = remoteRepository,
        localRepository = localRepository
    )

    @Provides
    fun provideRemoteRepository(
        apiUnit: ApiUnit
    ): RemoteChatsRepository = DefaultChatsRepository(
        apiUnit = apiUnit
    )

    @Provides
    fun provideLocalRepository(
        database: GPFDatabase
    ): LocalChatsRepository = RoomChatsRepository(
        db = database
    )
}