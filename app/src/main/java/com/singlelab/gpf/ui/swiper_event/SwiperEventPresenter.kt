package com.singlelab.gpf.ui.swiper_event

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.event.FilterEvent
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.new_features.games_model.GamePerson
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
        if (MyProfilePresenter.profile!!.login == null) {
            val auth = FirebaseAuth.getInstance()
            auth.currentUser!!.reload().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val db = FirebaseFirestore.getInstance()

                    db.collection("users")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                if (document.id == auth.currentUser!!.uid) {
                                    launchProfile(
                                        mapToObject(
                                            document.data,
                                            UserFirebase::class
                                        )
                                    )

                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                        }
                }
            }
        }
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
                                    tempLogin = user.login,
                                    tempName = user.name,
                                    tempGames = newGames,
                                    tempCity = user.city,
                                    tempDescription = user.description,
                                    tempAge = user.age.toString(),
                                    tempId = user.id,
                                    record2048 = user.recordMathCubes.toInt(),
                                    recordFlappyCat = user.recordFlappyCats.toInt(),
                                    recordPiano = user.recordPianoTiles.toInt(),
                                    recordTetris = user.recordTetris.toInt()
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

            runOnMainThread {

                if (events[iterEvent].tempGames.isNotEmpty()) {
                    var y = events[iterEvent].tempGames.first()
                    var qwe = if (y == "CS:GO") {
                        GamePerson.CSGO
                    } else if (y == "Overwatch") {
                        GamePerson.OVERWATCH
                    } else if (y == "Diablo 4") {
                        GamePerson.DIABLO
                    } else if (y == "Valorant") {
                        GamePerson.VALORANT
                    } else if (y == "PUBG") {
                        GamePerson.PUBG
                    } else if (y == "Dota 2") {
                        GamePerson.DOTA
                    } else {
                        null
                    }
                    if (qwe != null) {

                        while ((gameChosen.size != 0 && !gameChosen.contains(qwe)) || events[iterEvent].tempId == MyProfilePresenter.profile!!.personUid) {

                            iterEvent++
                            if (iterEvent == events.size)
                                iterEvent = 0

                            y = events[iterEvent].tempGames.first()
                            qwe = if (y == "CS:GO") {
                                GamePerson.CSGO
                            } else if (y == "Overwatch") {
                                GamePerson.OVERWATCH
                            } else if (y == "Diablo 4") {
                                GamePerson.DIABLO
                            } else if (y == "Valorant") {
                                GamePerson.VALORANT
                            } else if (y == "PUBG") {
                                GamePerson.PUBG
                            } else if (y == "Dota 2") {
                                GamePerson.DOTA
                            } else {
                                null
                            }

                            Log.d("asdxzca3", qwe.toString())
                        }
                    }
                }
                this.event = events[iterEvent]

                runOnMainThread {
                    iterEvent++
                    viewState.showEvent(this.event!!)
                }
            }
        }
    }

    fun acceptEvent() {
        invokeSuspend {

            if (MyProfilePresenter.profile!!.login == null) {
                val auth = FirebaseAuth.getInstance()
                auth.currentUser!!.reload().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val db = FirebaseFirestore.getInstance()

                        // get user from cloudstore
                        db.collection("users")
                            .get()
                            .addOnSuccessListener { result ->
                                var found = false
                                for (document in result) {
                                    if (document.id == auth.currentUser!!.uid) {
                                        launchProfile(
                                            mapToObject(
                                                document.data,
                                                UserFirebase::class
                                            )
                                        )
                                        ll1()
                                        found = true
                                    }
                                }
                                // if no id on cloudstore -> error
                                if (!found) {
                                    fail()
                                }
                            }
                            .addOnFailureListener { exception ->
                                fail()
                            }
                    } else {
                        fail()
                    }
                }
            } else {
                ll1()
            }

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

    private fun ll1() {
        if (!MyProfilePresenter.profile!!.likeTo.contains(this.event!!.tempId)) {
            MyProfilePresenter.profile!!.likeTo.add(this.event!!.tempId)
            if (MyProfilePresenter.profile!!.login != null) {
                Log.d("games123", MyProfilePresenter.profile!!.games.toString())
                val docData = hashMapOf(
                    "id" to MyProfilePresenter.profile!!.personUid,
                    "login" to MyProfilePresenter.profile!!.login!!,
                    "name" to MyProfilePresenter.profile!!.name,
                    "description" to MyProfilePresenter.profile!!.description!!,
                    "icon" to MyProfilePresenter.profile!!.imageContentUid!!,
                    "city" to MyProfilePresenter.profile!!.cityName,
                    "age" to MyProfilePresenter.profile!!.age,
                    "recordMathCubes" to MyProfilePresenter.profile!!.personRecord2048,
                    "recordFlappyCats" to MyProfilePresenter.profile!!.personRecordCats,
                    "recordPianoTiles" to MyProfilePresenter.profile!!.personRecordPiano,
                    "recordTetris" to MyProfilePresenter.profile!!.personRecordTetris,
                    "games" to MyProfilePresenter.profile!!.games,
                    "friends" to MyProfilePresenter.profile!!.friends,
                    "likeTo" to MyProfilePresenter.profile!!.likeTo
                )

                try {
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users").document(MyProfilePresenter.profile!!.personUid)
                        .set(docData).addOnSuccessListener {
                        }
                        .addOnFailureListener {
                            throw ApiException("")
                        }
                } catch (e: Exception) {
                }
            }
        }

        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val user = mapToObject(document.data, UserFirebase::class)
                    if (user.id != MyProfilePresenter.profile!!.personUid) {
                        if (user.likeTo.contains(MyProfilePresenter.profile!!.personUid) && MyProfilePresenter.profile!!.likeTo.contains(
                                user.id
                            )
                        ) {

                            MyProfilePresenter.profile!!.friends.add(user.id)
                            MyProfilePresenter.profile!!.likeTo.remove(user.id)
                            val liketo1 = user.likeTo.toMutableList()
                            val friends1 = user.friends.toMutableList()
                            friends1.add(MyProfilePresenter.profile!!.personUid)
                            liketo1.remove(MyProfilePresenter.profile!!.personUid)

                            user.likeTo = liketo1
                            user.friends = friends1
                            val docData = hashMapOf(
                                "id" to user.id,
                                "login" to user.login,
                                "name" to user.name,
                                "description" to user.description,
                                "icon" to user.icon,
                                "city" to user.city,
                                "age" to user.age,
                                "recordMathCubes" to user.recordMathCubes,
                                "recordFlappyCats" to user.recordFlappyCats,
                                "recordPianoTiles" to user.recordPianoTiles,
                                "recordTetris" to user.recordTetris,
                                "games" to user.games,
                                "friends" to user.friends,
                                "likeTo" to user.likeTo
                            )

                            try {
                                db.collection("users").document(user.id)
                                    .set(docData).addOnSuccessListener {
                                    }
                                    .addOnFailureListener {
                                        throw ApiException("")
                                    }
                            } catch (e: Exception) {
                            }

                            if (MyProfilePresenter.profile!!.login != null) {
                                val docData1 = hashMapOf(
                                    "id" to MyProfilePresenter.profile!!.personUid,
                                    "login" to MyProfilePresenter.profile!!.login!!,
                                    "name" to MyProfilePresenter.profile!!.name,
                                    "description" to MyProfilePresenter.profile!!.description!!,
                                    "icon" to MyProfilePresenter.profile!!.imageContentUid!!,
                                    "city" to MyProfilePresenter.profile!!.cityName,
                                    "age" to MyProfilePresenter.profile!!.age,
                                    "recordMathCubes" to MyProfilePresenter.profile!!.personRecord2048,
                                    "recordFlappyCats" to MyProfilePresenter.profile!!.personRecordCats,
                                    "recordPianoTiles" to MyProfilePresenter.profile!!.personRecordPiano,
                                    "recordTetris" to MyProfilePresenter.profile!!.personRecordTetris,
                                    "games" to MyProfilePresenter.profile!!.games,
                                    "friends" to MyProfilePresenter.profile!!.friends,
                                    "likeTo" to MyProfilePresenter.profile!!.likeTo
                                )

                                try {
                                    db.collection("users")
                                        .document(MyProfilePresenter.profile!!.personUid)
                                        .set(docData1).addOnSuccessListener {
                                        }
                                        .addOnFailureListener {
                                            throw ApiException("")
                                        }
                                } catch (e: Exception) {
                                }
                            }

                        }
                    }
                }
            }
    }

    private fun launchProfile(user: UserFirebase) {
        MyProfilePresenter.profile!!.games = mutableListOf()
        user.games.forEach {
            MyProfilePresenter.profile!!.games!!.add(GamePerson.valueOf(it))
        }
        MyProfilePresenter.profile!!.apply {
            login = user.login
            name = user.name
            description = user.description
            cityName = user.city
            friends = user.friends as MutableList<String>
            personUid = user.id
            likeTo = user.likeTo as MutableList<String>
            age = user.age.toInt()
            imageContentUid = user.icon
            personRecord2048 = user.recordMathCubes.toInt()
            personRecordCats = user.recordFlappyCats.toInt()
            personRecordPiano = user.recordPianoTiles.toInt()
            personRecordTetris = user.recordTetris.toInt()
        }

        checkFriends()
    }

    private fun checkFriends() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (MyProfilePresenter.profile!!.likeTo.contains(document.id)) {
                        launchProfile2(
                            mapToObject(
                                document.data,
                                UserFirebase::class
                            )
                        )
                    }
                }
            }
    }

    private fun launchProfile2(user: UserFirebase) {

        val db = FirebaseFirestore.getInstance()

        if (user.likeTo.contains(MyProfilePresenter.profile!!.personUid)) {

            MyProfilePresenter.profile!!.friends.add(user.id)
            MyProfilePresenter.profile!!.likeTo.remove(user.id)
            val liketo1 = user.likeTo.toMutableList()
            val friends1 = user.friends.toMutableList()
            friends1.add(MyProfilePresenter.profile!!.personUid)
            liketo1.remove(MyProfilePresenter.profile!!.personUid)

            user.likeTo = liketo1
            user.friends = friends1
            val docData = hashMapOf(
                "id" to user.id,
                "login" to user.login,
                "name" to user.name,
                "description" to user.description,
                "icon" to user.icon,
                "city" to user.city,
                "age" to user.age,
                "recordMathCubes" to user.recordMathCubes,
                "recordFlappyCats" to user.recordFlappyCats,
                "recordPianoTiles" to user.recordPianoTiles,
                "recordTetris" to user.recordTetris,
                "games" to user.games,
                "friends" to user.friends,
                "likeTo" to user.likeTo
            )

            try {
                db.collection("users").document(user.id)
                    .set(docData).addOnSuccessListener {
                    }
                    .addOnFailureListener {
                        throw ApiException("")
                    }
            } catch (e: Exception) {
            }

            if (MyProfilePresenter.profile!!.login != null) {
                val docData1 = hashMapOf(
                    "id" to MyProfilePresenter.profile!!.personUid,
                    "login" to MyProfilePresenter.profile!!.login!!,
                    "name" to MyProfilePresenter.profile!!.name,
                    "description" to MyProfilePresenter.profile!!.description!!,
                    "icon" to MyProfilePresenter.profile!!.imageContentUid!!,
                    "city" to MyProfilePresenter.profile!!.cityName,
                    "age" to MyProfilePresenter.profile!!.age,
                    "recordMathCubes" to MyProfilePresenter.profile!!.personRecord2048,
                    "recordFlappyCats" to MyProfilePresenter.profile!!.personRecordCats,
                    "recordPianoTiles" to MyProfilePresenter.profile!!.personRecordPiano,
                    "recordTetris" to MyProfilePresenter.profile!!.personRecordTetris,
                    "games" to MyProfilePresenter.profile!!.games,
                    "friends" to MyProfilePresenter.profile!!.friends,
                    "likeTo" to MyProfilePresenter.profile!!.likeTo
                )

                try {
                    db.collection("users")
                        .document(MyProfilePresenter.profile!!.personUid)
                        .set(docData1).addOnSuccessListener {
                        }
                        .addOnFailureListener {
                            throw ApiException("")
                        }
                } catch (e: Exception) {
                }
            }
        }
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

        var gameChosen = mutableListOf<GamePerson>()
        var isCompetitive: Int = 0
        var iterEvent = 0
        var events = mutableListOf<Event>()
    }
}