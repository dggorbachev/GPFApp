package com.singlelab.gpf.ui.event.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.database.repository.EventsSummaryRepository
import com.singlelab.gpf.ui.event.EventPresenter
import com.singlelab.gpf.ui.event.interactor.EventInteractor
import com.singlelab.gpf.ui.event.interactor.EventInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object EventModule {
    @Provides
    fun providePresenter(interactor: EventInteractor): EventPresenter {
        return EventPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(
        repository: EventsRepository,
        localRepository: EventsSummaryRepository
    ): EventInteractor {
        return EventInteractorImpl(repository, localRepository)
    }
}