package com.singlelab.gpf.ui.person.di

import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.person.PersonRepository
import com.singlelab.net.repositories.person.PersonRepositoryImpl
import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.person.PersonPresenter
import com.singlelab.gpf.ui.person.interactor.PersonInteractor
import com.singlelab.gpf.ui.person.interactor.PersonInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object PersonModule {

    @Provides
    fun providePresenter(
        interactor: PersonInteractor
    ): PersonPresenter {
        return PersonPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: PersonRepository): PersonInteractor {
        return PersonInteractorImpl(repository)
    }

    @Provides
    fun providesRepository(apiUnit: ApiUnit): PersonRepository {
        return PersonRepositoryImpl(apiUnit)
    }
}