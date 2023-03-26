package com.singlelab.gpf.ui.reg.interactor

import com.singlelab.net.model.person.ProfileRequest

interface RegistrationInteractor {
    suspend fun registration(profile: ProfileRequest)
}