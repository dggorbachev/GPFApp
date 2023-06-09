package com.singlelab.gpf.ui.swiper_event.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.swiper_event.SwiperEventPresenter
import com.singlelab.gpf.ui.swiper_event.interactor.SwiperEventInteractor
import com.singlelab.gpf.ui.swiper_event.interactor.SwiperEventInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import com.singlelab.net.repositories.person.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object SwiperEventModule {
    @Provides
    fun providePresenter(interactor: SwiperEventInteractor): SwiperEventPresenter {
        return SwiperEventPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(
        repository: EventsRepository,
        personRepository: PersonRepository
    ): SwiperEventInteractor {
        return SwiperEventInteractorImpl(repository, personRepository)
    }
}