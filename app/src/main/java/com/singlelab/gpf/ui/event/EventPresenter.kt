package com.singlelab.gpf.ui.event

import android.graphics.Bitmap
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.event.EventStatus
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.event.interactor.EventInteractor
import com.singlelab.gpf.util.formatToUTC
import com.singlelab.gpf.util.resize
import com.singlelab.gpf.util.toBase64
import com.singlelab.gpf.util.toLongTime
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.model.auth.AuthData
import com.singlelab.net.model.event.ParticipantRequest
import com.singlelab.net.model.event.ParticipantStatus
import com.singlelab.net.model.event.UpdateEventRequest
import moxy.InjectViewState
import java.util.*
import javax.inject.Inject

@InjectViewState
class EventPresenter @Inject constructor(
    private val interactor: EventInteractor,
    preferences: Preferences?
) : BasePresenter<EventView>(preferences, interactor as BaseInteractor) {

    var event: Event? = null

    var newDateStart: Calendar? = null
    var newDateEnd: Calendar? = null

    fun loadEvent(uid: String) {
        viewState.showLoading(true)
        invokeSuspend {
            try {
                val event = interactor.getEvent(uid)
                runOnMainThread {
                    viewState.showLoading(false)
                    this.event = event
                    event?.let {
                        viewState.showEvent(it)
                    }
                }
            } catch (e: ApiException) {
                showEventFromCache(uid)
                runOnMainThread {
                    viewState.showLoading(false)
                    viewState.showError(e.message)
                }
            }
        }
    }

    private suspend fun showEventFromCache(uid: String) {
        val event = interactor.getEventFromCache(uid)
        if (event != null) {
            runOnMainThread {
                viewState.showLoading(false)
                viewState.showEvent(event)
            }
        }
    }

    fun updateEvent(
        description: String? = null,
        images: List<Bitmap>? = null,
        xCoordinate: Double? = null,
        yCoordinate: Double? = null
    ) {
        event?.eventUid?.let { uid ->
            viewState.showLoading(isShow = true, withoutBackground = true)
            invokeSuspend {
                var primaryImage: String? = null
                var extraImages: List<String>? = null
                if (!images.isNullOrEmpty()) {
                    if (event?.eventPrimaryImageContentUid == null) {
                        primaryImage = images[0].resize().toBase64()
                        extraImages = getImagesStr(images.toMutableList())
                    } else {
                        extraImages = images.map { it.resize().toBase64() }
                    }
                }
                try {
                    event = interactor.updateEvent(
                        UpdateEventRequest(
                            eventUid = uid,
                            description = description,
                            primaryImage = primaryImage,
                            extraImages = extraImages,
                            xCoordinate = xCoordinate,
                            yCoordinate = yCoordinate
                        )
                    )
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        event?.let {
                            viewState.showEvent(it)
                        }
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    private fun getImagesStr(images: MutableList<Bitmap>): List<String>? {
        val newImages = mutableListOf<String>()
        if (images.isNotEmpty()) {
            images.removeAt(0)
        }
        newImages.addAll(images.map { it.resize().toBase64() })
        return newImages
    }

    fun onClickAdministrator() {
        event?.administrator?.personUid?.let {
            viewState.toProfile(it)
        }
    }

    fun getEventUid(): String? = event?.eventUid

    fun getParticipant(withNotApproved: Boolean): Array<Person>? {
        event?.let { event ->
            return if (withNotApproved) {
                val allParticipants = mutableListOf<Person>()
                allParticipants.addAll(event.notApprovedParticipants)
                allParticipants.addAll(event.participants)
                allParticipants.map { it }.toTypedArray()
            } else {
                event.participants.map { it }.toTypedArray()
            }
        }
        return null
    }

    fun acceptEvent(personUid: String, eventUid: String?) {
        eventUid ?: return
        viewState.showLoading(true)
        invokeSuspend {
            try {
                val event = interactor.acceptEvent(
                    ParticipantRequest(
                        personUid,
                        eventUid,
                        ParticipantStatus.ACTIVE.id
                    )
                )
                runOnMainThread {
                    viewState.showLoading(false)
                    event?.let {
                        viewState.showEvent(event)
                    }
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(false)
                    viewState.showError(e.message)
                }
            }
        }
    }

    fun joinEvent() {
        val uid = AuthData.uid ?: return
        event?.eventUid?.let { eventUid ->
            viewState.showLoading(isShow = true, withoutBackground = true)
            invokeSuspend {
                try {
                    event = interactor.joinEvent(
                        ParticipantRequest(
                            uid,
                            eventUid,
                            if (event!!.isOpenForInvitations)
                                ParticipantStatus.ACTIVE.id
                            else
                                ParticipantStatus.WAITING_FOR_APPROVE_FROM_EVENT.id
                        )
                    )
                    runOnMainThread {
                        viewState.showEvent(event!!)
                        viewState.showLoading(isShow = false, withoutBackground = true)
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    fun rejectEvent(personUid: String, eventUid: String?) {
        eventUid ?: return
        viewState.showLoading(true)
        invokeSuspend {
            try {
                interactor.rejectEvent(personUid, eventUid)
                runOnMainThread {
                    viewState.showLoading(false)
                    viewState.onRejectedEvent()
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(false)
                    viewState.showError(e.message)
                }
            }
        }
    }

    fun cancelEvent() {
        event?.eventUid?.let { uid ->
            viewState.showLoading(isShow = true, withoutBackground = true)
            invokeSuspend {
                try {
                    val event =
                        interactor.updateEvent(UpdateEventRequest(uid, EventStatus.CANCELLED.id))
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        event?.let {
                            viewState.showEvent(event)
                        }
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    fun isAdministrator() = event?.administrator?.personUid == AuthData.uid

    fun onClickInviteFriends() {
        val allParticipantIds = event?.getAllParticipants()?.map { it.personUid }
        event?.eventUid?.let { eventUid ->
            viewState.toInviteFriends(eventUid, allParticipantIds)
        }
    }

    fun getAvailablePromoReward() {
        event?.let {
            if (isAdministrator() && it.isCanReceiveReward() && it.promoRequestUid == null
                && preferences != null && preferences.getEventPromoRewardEnabled()
                && it.cityId != null
            ) {
                invokeSuspend {
                    val promoReward = interactor.checkPromoReward(it.cityId)
                    if (promoReward != null && promoReward.isCitySuitableForPromoReward) {
                        runOnMainThread {
                            viewState.showPromoReceiveButton(true)
                        }
                    }
                }
            } else {
                runOnMainThread {
                    viewState.showPromoReceiveButton(false)
                }
            }
        }
    }

    fun sendReport(reasonReport: String) {
        event?.eventUid?.let { uid ->
            viewState.showLoading(true)
            invokeSuspend {
                try {
                    interactor.sendReport(uid, reasonReport)
                    runOnMainThread {
                        viewState.showLoading(false)
                        viewState.showSuccessReport()
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showLoading(false)
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    fun saveNewDate(year: Int, month: Int, day: Int, isStart: Boolean) {
        if (isStart) {
            if (newDateStart == null) {
                newDateStart = Calendar.getInstance()
            }
            newDateStart!!.set(Calendar.YEAR, year)
            newDateStart!!.set(Calendar.MONTH, month)
            newDateStart!!.set(Calendar.DAY_OF_MONTH, day)
        } else {
            if (newDateEnd == null) {
                newDateEnd = Calendar.getInstance()
            }
            newDateEnd!!.set(Calendar.YEAR, year)
            newDateEnd!!.set(Calendar.MONTH, month)
            newDateEnd!!.set(Calendar.DAY_OF_MONTH, day)
        }
    }

    fun saveCurrentTime(hours: Int, minutes: Int, isStart: Boolean) {
        if (isStart) {
            if (newDateStart == null) {
                newDateStart = Calendar.getInstance()
            }
            newDateStart!!.set(Calendar.HOUR_OF_DAY, hours)
            newDateStart!!.set(Calendar.MINUTE, minutes)
        } else {
            if (newDateEnd == null) {
                newDateEnd = Calendar.getInstance()
            }
            newDateEnd!!.set(Calendar.HOUR_OF_DAY, hours)
            newDateEnd!!.set(Calendar.MINUTE, minutes)
            updateDate(newDateStart, newDateEnd)
        }
    }

    private fun updateDate(newDateStart: Calendar?, newDateEnd: Calendar?) {
        event?.eventUid?.let { uid ->
            viewState.showLoading(isShow = true, withoutBackground = true)
            invokeSuspend {
                try {
                    val request = UpdateEventRequest(
                        eventUid = uid,
                        startTime = newDateStart?.time.formatToUTC(Const.DATE_FORMAT_TIME_ZONE),
                        endTime = newDateEnd?.time.formatToUTC(Const.DATE_FORMAT_TIME_ZONE)
                    )
                    val event = interactor.updateEvent(request)
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        event?.let {
                            viewState.showEvent(it)
                        }
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showLoading(isShow = false, withoutBackground = true)
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    fun getStartTime(): Calendar? {
        val longTime = event?.startTime?.toLongTime(Const.DATE_FORMAT_TIME_ZONE) ?: return null
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = longTime
        return calendar
    }

    fun isCanUpdateDate() = isAdministrator() && event != null && event!!.isActive()
}