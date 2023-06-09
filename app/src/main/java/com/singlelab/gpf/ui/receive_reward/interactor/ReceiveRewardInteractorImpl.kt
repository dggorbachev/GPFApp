package com.singlelab.gpf.ui.receive_reward.interactor

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.net.model.promo.PromoRequest
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.net.repositories.events.EventsRepository

class ReceiveRewardInteractorImpl(private val repository: EventsRepository) :
    BaseInteractor(repository as BaseRepository), ReceiveRewardInteractor {

    override suspend fun sendPromoRequest(request: PromoRequest) {
        repository.sendPromoRequest(request)
    }
}