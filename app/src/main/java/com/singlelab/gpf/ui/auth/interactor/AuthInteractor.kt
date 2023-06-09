package com.singlelab.gpf.ui.auth.interactor

import com.singlelab.gpf.model.auth.Auth

interface AuthInteractor {
    suspend fun sendCode(phone: String): String?

    suspend fun auth(phone: String, code: String): Auth?

    suspend fun isPersonFilled(): Boolean
}