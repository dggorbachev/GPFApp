package com.singlelab.gpf.ui.person

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.person.interactor.PersonInteractor
import com.singlelab.gpf.ui.swiper_event.SwiperEventPresenter
import com.singlelab.net.exceptions.ApiException
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class PersonPresenter @Inject constructor(
    private var interactor: PersonInteractor,
    preferences: Preferences?
) : BasePresenter<PersonView>(preferences, interactor as BaseInteractor) {

    private var profile: Profile? = null

    fun loadProfile(personUid: String) {

        if (SwiperEventPresenter.events.size == 0) {
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
                            SwiperEventPresenter.events.add(
                                Event(
                                    tempLogin = user.login,
                                    tempImage = user.icon,
                                    tempName = user.name,
                                    tempGames = newGames,
                                    tempCity = user.city,
                                    tempDescription = user.description,
                                    tempAge = user.age.toString(),
                                    record2048 = user.recordMathCubes.toInt(),
                                    recordFlappyCat = user.recordFlappyCats.toInt(),
                                    recordPiano = user.recordPianoTiles.toInt(),
                                    recordTetris = user.recordTetris.toInt(),
                                    tempId = user.id
                                )
                            )
                        }
                    }

                    SwiperEventPresenter.events =
                        SwiperEventPresenter.events.shuffled().toMutableList()

                    loadProf(SwiperEventPresenter.events.find { it.tempId == personUid }!!)
                }
                .addOnFailureListener { exception ->
                }
        } else {
            loadProf(SwiperEventPresenter.events.find { it.tempId == personUid }!!)
        }
    }

    private fun loadProf(person: Event) {
        runOnMainThread {
            profile = Profile(
                personUid = person.tempId,
                name = person.tempName,
                login = person.tempLogin,
                description = person.tempDescription,
                cityName = person.tempCity,
                age = person.tempAge.toInt(),
                imageContentUid = person.tempImage,
                isFriend = MyProfilePresenter.profile!!.friends.any { it == person.tempId },
                cityId = 1
            )

//            if (personUid == "1") {
//                if (MyProfilePresenter.myFriends.any { it.name == "Вячеслав" }) {
//                    Profile(
//                        "1",
//                        "vyach123",
//                        "Вячеслав",
//                        "Лучший игрок в CS:GO в Митино",
//                        1,
//                        "Москва, Россия",
//                        20,
//                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
//                        true,
//                        arrayListOf()
//                    )
//                } else {
//                    Profile(
//                        "1",
//                        "vyach123",
//                        "Вячеслав",
//                        "Лучший игрок в CS:GO в Митино",
//                        1,
//                        "Москва, Россия",
//                        20,
//                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
//                        false,
//                        arrayListOf()
//                    )
//                }
//            } else {
//                if (MyProfilePresenter.myFriends.any { it.name == "Александр" }) {
//                    Profile(
//                        "2",
//                        "sashaKolom",
//                        "Александр",
//                        "Бросил доту и вам советую",
//                        1,
//                        "Санкт-Петербург, Россия",
//                        21,
//                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
//                        true,
//                        arrayListOf()
//                    )
//                } else {
//                    Profile(
//                        "2",
//                        "sashaKolom",
//                        "Александр",
//                        "Бросил доту и вам советую",
//                        1,
//                        "Санкт-Петербург, Россия",
//                        21,
//                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
//                        false,
//                        arrayListOf()
//                    )
//                }
//            }

            viewState.showProfile(profile!!)
        }
    }

    fun addToFriends(profile: Profile) {
//        viewState.showLoading(true)

        profile.isFriend = true
        val l = MyProfilePresenter.profile!!.friends.toMutableList()
        l.add(profile.personUid)
        MyProfilePresenter.profile!!.friends = l

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
            "newFriends" to MyProfilePresenter.profile!!.newFriends,
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
        viewState.showProfile(profile)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeFromFriends(profile: Profile) {
//        viewState.showLoading(true)

        MyProfilePresenter.myFriends.removeIf { it.personUid == profile.personUid }

        profile.isFriend = false
        val l = MyProfilePresenter.profile!!.friends.toMutableList()
        l.removeIf { it == profile.personUid }
        MyProfilePresenter.profile!!.friends = l


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
            "newFriends" to MyProfilePresenter.profile!!.newFriends,
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

        viewState.showProfile(profile)
    }

    fun sendReport(reasonReport: String) {
        profile?.personUid?.let { uid ->
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
}