package com.singlelab.gpf.ui.swiper_event

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.event.FilterEvent
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.swiper_event.interactor.SwiperEventInteractor
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.model.auth.AuthData
import kotlinx.coroutines.delay
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SwiperEventPresenter @Inject constructor(
    private val interactor: SwiperEventInteractor,
    preferences: Preferences?
) : BasePresenter<SwiperEventView>(preferences, interactor as BaseInteractor) {

    var event: Event? = null

    var filterEvent = FilterEvent(cityId = AuthData.cityId, cityName = AuthData.cityName)

    override fun onFirstViewAttach() {
        events = events.shuffled().toMutableList()
        super.onFirstViewAttach()
        loadRandomEvent(true)
    }

    private fun sendPushToken(token: String?) {
        token ?: return
        invokeSuspend {
            try {
                preferences?.setFirstLaunch(false)
            } catch (e: ApiException) {
            }
        }
    }

    fun applyFilter(filterEvent: FilterEvent) {
        if (this.filterEvent != filterEvent) {
            this.filterEvent = filterEvent
            loadRandomEvent()
        }
    }

    private fun fail() {
        viewState.showError("Error to load users. Try again later!")
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun loadRandomEvent(isFirstAttach: Boolean = false) {
        if (isFirstAttach) {
            val db = FirebaseFirestore.getInstance()

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val user = mapToObject(document.data, UserFirebase::class)
                        if (user.id != MyProfilePresenter.profile!!.personUid) {
                            val newGames = mutableListOf<String>()
                            user.games.forEach {
                                when (it) {
                                    "DOTA" -> {
                                        newGames.add("Dota 2")
                                    }

                                    "CSGO" -> {
                                        newGames.add("CS:GO")
                                    }

                                    "OVERWATCH" -> {
                                        newGames.add("Overwatch")
                                    }

                                    "VALORANT" -> {
                                        newGames.add("Valorant")
                                    }

                                    "PUBG" -> {
                                        newGames.add("PUBG")
                                    }

                                    "DIABLO" -> {
                                        newGames.add("Diablo 4")
                                    }
                                }
                            }
                            events.add(
                                Event(
                                    tempImage = user.icon,
                                    tempName = user.name,
                                    tempGames = newGames,
                                    tempCity = user.city,
                                    tempDescription = user.description,
                                    tempAge = user.age.toString(),
                                    record2048 = user.recordMathCubes.toInt(),
                                    recordFlappyCat = user.recordFlappyCats.toInt(),
                                    recordPiano = user.recordPianoTiles.toInt()
                                )
                            )
                        }
                    }

                    events = events.shuffled().toMutableList()

                    randomEvent()
                }
                .addOnFailureListener { exception ->
                    fail()
                }
        } else {
            randomEvent()
        }
    }

    private fun randomEvent() {
        invokeSuspend {
            if (iterEvent == events.size)
                iterEvent = 0

            when (isCompetitive) {
                -1 -> {
                    runOnMainThread {

                        while (events[iterEvent].tempGames.first() == "Diablo 4") {
                            iterEvent++
                            if (iterEvent == events.size)
                                iterEvent = 0
                        }
                        this.event = events[iterEvent]

                        runOnMainThread {
                            viewState.showEvent(this.event!!)
                        }
                    }
                }

                0 -> {
                    runOnMainThread {

                        this.event = events[iterEvent]

                        runOnMainThread {
                            iterEvent++
                            viewState.showEvent(this.event!!)
                        }
                    }
                }

                1 -> {
                    runOnMainThread {

                        while (events[iterEvent].tempGames.first() != "Diablo 4") {
                            iterEvent++
                            if (iterEvent == events.size)
                                iterEvent = 0
                        }
                        this.event = events[iterEvent]

                        runOnMainThread {
                            viewState.showEvent(this.event!!)
                        }
                    }
                }
            }
        }
    }

    fun acceptEvent() {
        invokeSuspend {
            delay(500)
            runOnMainThread {
                loadRandomEvent()
            }
        }
//        val uid = AuthData.uid ?: return
//        event?.eventUid?.let { eventUid ->
//            viewState.showLoading(isShow = true, withoutBackground = true)
//            invokeSuspend {
//                try {
//                    interactor.acceptEvent(
//                        ParticipantRequest(
//                            uid,
//                            eventUid,
//                            if (event!!.isOpenForInvitations) ParticipantStatus.ACTIVE.id else ParticipantStatus.WAITING_FOR_APPROVE_FROM_EVENT.id
//                        )
//                    )
//                    runOnMainThread {
//                        viewState.toAcceptedEvent(event!!.isOpenForInvitations, eventUid)
//                        event = null
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                    }
//                } catch (e: ApiException) {
//                    runOnMainThread {
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                        viewState.showError(e.message)
//                    }
//                }
//            }
//        }
    }

    fun rejectEvent() {
        invokeSuspend {
            delay(500)
            runOnMainThread {
                loadRandomEvent()
            }
        }

//        event?.eventUid?.let { eventUid ->
//            viewState.showLoading(isShow = true, withoutBackground = true)
//            invokeSuspend {
//                try {
//                    interactor.rejectEvent(eventUid)
//                    runOnMainThread {
//                        event = null
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                        loadRandomEvent()
//                    }
//                } catch (e: ApiException) {
//                    runOnMainThread {
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                        viewState.showError(e.message)
//                    }
//                }
//            }
//        }
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

    companion object {

        var isCompetitive: Int = 0
        var iterEvent = 0
        var events = mutableListOf<Event>()
    }
}