package com.singlelab.gpf.ui.image_slider.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.image_slider.SliderPresenter
import com.singlelab.gpf.ui.image_slider.interactor.SliderInteractor
import com.singlelab.gpf.ui.image_slider.interactor.SliderInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object SliderModule {
    @Provides
    fun providePresenter(interactor: SliderInteractor): SliderPresenter {
        return SliderPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: EventsRepository): SliderInteractor {
        return SliderInteractorImpl(repository)
    }
}