package com.singlelab.gpf.ui.feedback.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.feedback.FeedbackPresenter
import com.singlelab.gpf.ui.feedback.interactor.FeedbackInteractor
import com.singlelab.gpf.ui.feedback.interactor.FeedbackInteractorImpl
import com.singlelab.net.repositories.person.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object FeedbackModule {
    @Provides
    fun providePresenter(interactor: FeedbackInteractor): FeedbackPresenter {
        return FeedbackPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: PersonRepository): FeedbackInteractor {
        return FeedbackInteractorImpl(repository)
    }
}