package com.singlelab.gpf.ui.swiper_person.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.swiper_person.SwiperPersonPresenter
import com.singlelab.gpf.ui.swiper_person.interactor.SwiperPersonInteractor
import com.singlelab.gpf.ui.swiper_person.interactor.SwiperPersonInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import com.singlelab.net.repositories.person.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object SwiperPersonModule {
    @Provides
    fun providePresenter(interactor: SwiperPersonInteractor): SwiperPersonPresenter {
        return SwiperPersonPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(
        repository: EventsRepository,
        personRepository: PersonRepository
    ): SwiperPersonInteractor {
        return SwiperPersonInteractorImpl(repository, personRepository)
    }
}