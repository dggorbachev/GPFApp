package com.singlelab.gpf.ui.friends.di

import com.singlelab.net.ApiUnit
import com.singlelab.net.repositories.friends.FriendsRepository
import com.singlelab.net.repositories.friends.FriendsRepositoryImpl
import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.friends.FriendsPresenter
import com.singlelab.gpf.ui.friends.interactor.FriendsInteractor
import com.singlelab.gpf.ui.friends.interactor.FriendsInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object FriendsModule {

    @Provides
    fun providePresenter(
        interactor: FriendsInteractor
    ): FriendsPresenter {
        return FriendsPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: FriendsRepository): FriendsInteractor {
        return FriendsInteractorImpl(repository)
    }

    @Provides
    fun providesRepository(apiUnit: ApiUnit): FriendsRepository {
        return FriendsRepositoryImpl(apiUnit)
    }
}