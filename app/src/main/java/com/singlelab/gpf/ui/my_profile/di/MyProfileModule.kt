package com.singlelab.gpf.ui.my_profile.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.repository.ProfileRepository
import com.singlelab.gpf.database.repository.RoomProfileRepository
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.my_profile.interactor.MyProfileInteractor
import com.singlelab.gpf.ui.my_profile.interactor.MyProfileInteractorImpl
import com.singlelab.net.repositories.person.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object MyProfileModule {

    @Provides
    fun providePresenter(
        interactor: MyProfileInteractor
    ): MyProfilePresenter {
        return MyProfilePresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(
        repository: PersonRepository,
        localRepository: ProfileRepository,
        database: GPFDatabase
    ): MyProfileInteractor {
        return MyProfileInteractorImpl(repository, localRepository, database)
    }

    @Provides
    fun provideLocalRepository(database: GPFDatabase): ProfileRepository {
        return RoomProfileRepository(database)
    }
}