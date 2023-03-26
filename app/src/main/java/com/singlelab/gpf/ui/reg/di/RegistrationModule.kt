package com.singlelab.gpf.ui.reg.di

import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.reg.RegistrationRepository
import com.singlelab.net.repositories.reg.RegistrationRepositoryImpl
import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.reg.RegistrationPresenter
import com.singlelab.gpf.ui.reg.interactor.RegistrationInteractor
import com.singlelab.gpf.ui.reg.interactor.RegistrationInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object RegistrationModule {
    @Provides
    fun providePresenter(interactor: RegistrationInteractor): RegistrationPresenter {
        return RegistrationPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: RegistrationRepository): RegistrationInteractor {
        return RegistrationInteractorImpl(repository)
    }

    @Provides
    fun provideRepository(apiUnit: ApiUnit): RegistrationRepository {
        return RegistrationRepositoryImpl(apiUnit)
    }
}