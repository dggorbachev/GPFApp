package com.singlelab.gpf.ui.filters.person.di

import com.singlelab.gpf.ui.filters.person.FilterPersonPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object FilterPersonModule {
    @Provides
    fun providePresenter(): FilterPersonPresenter {
        return FilterPersonPresenter()
    }
}