package com.singlelab.gpf.ui.search_event.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.search_event.SearchEventPresenter
import com.singlelab.gpf.ui.search_event.interactor.SearchEventInteractor
import com.singlelab.gpf.ui.search_event.interactor.SearchEventInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object SearchEventModule {
    @Provides
    fun providePresenter(interactor: SearchEventInteractor): SearchEventPresenter {
        return SearchEventPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: EventsRepository): SearchEventInteractor {
        return SearchEventInteractorImpl(repository)
    }
}