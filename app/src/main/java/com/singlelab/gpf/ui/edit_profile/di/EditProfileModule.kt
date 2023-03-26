package com.singlelab.gpf.ui.edit_profile.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.edit_profile.EditProfilePresenter
import com.singlelab.gpf.ui.edit_profile.interactor.EditProfileInteractor
import com.singlelab.gpf.ui.edit_profile.interactor.EditProfileInteractorImpl
import com.singlelab.net.repositories.person.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object EditProfileModule {
    @Provides
    fun providePresenter(interactor: EditProfileInteractor): EditProfilePresenter {
        return EditProfilePresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: PersonRepository): EditProfileInteractor {
        return EditProfileInteractorImpl(repository)
    }
}