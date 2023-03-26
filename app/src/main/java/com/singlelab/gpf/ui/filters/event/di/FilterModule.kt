package com.singlelab.gpf.ui.filters.event.di

import com.singlelab.gpf.GPFApplication
import com.singlelab.gpf.ui.filters.event.FilterPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
object FilterModule {
    @Provides
    fun providePresenter(): FilterPresenter {
        return FilterPresenter(
            GPFApplication.preferences
        )
    }
}