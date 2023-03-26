package com.singlelab.gpf.ui.auth.di

import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.auth.AuthRepository
import com.singlelab.net.repositories.auth.AuthRepositoryImpl
import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.ui.auth.AuthPresenter
import com.singlelab.gpf.ui.auth.interactor.AuthInteractor
import com.singlelab.gpf.ui.auth.interactor.AuthInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object AuthModule {
    @Provides
    fun providePresenter(interactor: AuthInteractor): AuthPresenter {
        return AuthPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: AuthRepository, database: GPFDatabase): AuthInteractor {
        return AuthInteractorImpl(repository, database)
    }

    @Provides
    fun provideRepository(apiUnit: ApiUnit): AuthRepository {
        return AuthRepositoryImpl(apiUnit)
    }
}