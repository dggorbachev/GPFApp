package com.singlelab.gpf.ui.receive_reward.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.receive_reward.ReceiveRewardPresenter
import com.singlelab.gpf.ui.receive_reward.interactor.ReceiveRewardInteractor
import com.singlelab.gpf.ui.receive_reward.interactor.ReceiveRewardInteractorImpl
import com.singlelab.net.repositories.events.EventsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object ReceiveRewardModule {
    @Provides
    fun providePresenter(interactor: ReceiveRewardInteractor): ReceiveRewardPresenter {
        return ReceiveRewardPresenter(interactor, GPFApplication.preferences)
    }

    @Provides
    fun provideInteractor(repository: EventsRepository): ReceiveRewardInteractor {
        return ReceiveRewardInteractorImpl(repository)
    }
}