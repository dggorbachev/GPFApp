package com.singlelab.gpf.ui.creating_event.di

import com.singlelab.net.repositories.events.EventsRepository
import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.creating_event.CreatingEventPresenter
import com.singlelab.gpf.ui.creating_event.interactor.CreatingEventInteractor
import com.singlelab.gpf.ui.creating_event.interactor.CreatingEventInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object CreatingEventModule {
    @Provides
    fun providePresenter(interactor: CreatingEventInteractor): CreatingEventPresenter {
        return CreatingEventPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: EventsRepository): CreatingEventInteractor {
        return CreatingEventInteractorImpl(repository)
    }
}