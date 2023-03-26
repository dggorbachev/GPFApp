package com.singlelab.gpf.ui.events.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.repository.EventsSummaryRepository
import com.singlelab.gpf.database.repository.RoomEventsSummaryRepository
import com.singlelab.gpf.ui.events.EventsPresenter
import com.singlelab.gpf.ui.events.interactor.EventsInteractor
import com.singlelab.gpf.ui.events.interactor.EventsInteractorImpl
import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.events.EventsRepository
import com.singlelab.net.repositories.events.EventsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object EventsModule {
    @Provides
    fun providePresenter(interactor: EventsInteractor): EventsPresenter {
        return EventsPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(
        repository: EventsRepository,
        localRepository: EventsSummaryRepository
    ): EventsInteractor {
        return EventsInteractorImpl(repository, localRepository)
    }

    @Provides
    fun provideRepository(apiUnit: ApiUnit): EventsRepository {
        return EventsRepositoryImpl(apiUnit)
    }

    @Provides
    fun provideLocalRepository(database: GPFDatabase): EventsSummaryRepository {
        return RoomEventsSummaryRepository(database)
    }
}