package com.singlelab.gpf.ui.edit_profile.interactor

import com.singlelab.gpf.model.profile.NewProfile

interface EditProfileInteractor {
    suspend fun updateProfile(newProfile: NewProfile)
}