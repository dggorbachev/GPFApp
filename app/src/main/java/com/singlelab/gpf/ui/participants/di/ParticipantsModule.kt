package com.singlelab.gpf.ui.participants.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.participants.ParticipantsPresenter
import com.singlelab.gpf.ui.participants.interactor.ParticipantsInteractor
import com.singlelab.gpf.ui.participants.interactor.ParticipantsInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object ParticipantsModule {

    @Provides
    fun providePresenter(
        interactor: ParticipantsInteractor
    ): ParticipantsPresenter {
        return ParticipantsPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: EventsRepository): ParticipantsInteractor {
        return ParticipantsInteractorImpl(repository)
    }

}