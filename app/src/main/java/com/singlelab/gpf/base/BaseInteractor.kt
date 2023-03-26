package com.singlelab.gpf.base

import com.singlelab.gpf.model.profile.PersonNotifications
import com.singlelab.gpf.model.promo.PromoInfo
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.net.repositories.OnRefreshTokenListener

open class BaseInteractor(private val baseRepository: BaseRepository) {

    fun setOnRefreshTokenListener(listener: OnRefreshTokenListener?) {
        baseRepository.setOnRefreshTokenListener(listener)
    }

    suspend fun getNotifications() =
        PersonNotifications.fromResponse(baseRepository.getNotifications())

    suspend fun getPromo() = PromoInfo.fromResponse(baseRepository.getPromo())
}