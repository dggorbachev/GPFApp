package com.singlelab.gpf.ui.person

import android.os.Build
import androidx.annotation.RequiresApi
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.person.interactor.PersonInteractor
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
//        viewState.showLoading(true)
//        invokeSuspend {
//            try {
//                val profile = interactor.loadPerson(personUid)
//                runOnMainThread {
//                    viewState.showLoading(false)
//                    if (profile != null) {
//                        this.profile = profile
        runOnMainThread {
            profile = if (personUid == "1") {
                if (MyProfilePresenter.myFriends.any { it.name == "Вячеслав" }) {
                    Profile(
                        "1",
                        "vyach123",
                        "Вячеслав",
                        "Лучший игрок в CS:GO в Митино",
                        1,
                        "Москва, Россия",
                        20,
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        arrayListOf()
                    )
                } else {
                    Profile(
                        "1",
                        "vyach123",
                        "Вячеслав",
                        "Лучший игрок в CS:GO в Митино",
                        1,
                        "Москва, Россия",
                        20,
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        false,
                        arrayListOf()
                    )
                }
            } else {
                if (MyProfilePresenter.myFriends.any { it.name == "Александр" }) {
                    Profile(
                        "2",
                        "sashaKolom",
                        "Александр",
                        "Бросил доту и вам советую",
                        1,
                        "Санкт-Петербург, Россия",
                        21,
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        arrayListOf()
                    )
                } else {
                    Profile(
                        "2",
                        "sashaKolom",
                        "Александр",
                        "Бросил доту и вам советую",
                        1,
                        "Санкт-Петербург, Россия",
                        21,
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        false,
                        arrayListOf()
                    )
                }
            }

            viewState.showProfile(profile!!)
        }
//                    }
//                }
//            } catch (e: ApiException) {
//                runOnMainThread {
//                    viewState.showLoading(false)
//                    viewState.showError(e.message)
//                }
//            }
//        }
    }

    fun addToFriends(profile: Profile) {
//        viewState.showLoading(true)
//        invokeSuspend {
//            try {
//                interactor.addToFriends(personUid)
//                runOnMainThread {
//                    viewState.showLoading(false)
//                    profile?.let {
//                        it.isFriend = true
//                        viewState.showProfile(it)
//                    }
//                }
//            } catch (e: ApiException) {
//                runOnMainThread {
//                    viewState.showLoading(false)
//                    viewState.showError(e.message)
//                }
//            }
//        }
        if (profile.personUid == "1") {
            MyProfilePresenter.myFriends.add(
                Person(
                    "1",
                    "Вячеслав",
                    "vyach123",
                    "Бросил доту и вам советую",
                    21,
                    "Москва, Россия",
                    "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                    true,
                    false,
                    false,
                    null
                )
            )
        } else if (profile.personUid == "2") {
            MyProfilePresenter.myFriends.add(
                Person(
                    "2",
                    "Александр",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                )
            )
        }
        profile.isFriend = true
        viewState.showProfile(profile)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeFromFriends(profile: Profile) {
//        viewState.showLoading(true)
//        invokeSuspend {
//            try {
//                interactor.removeFromFriends(personUid)
//                runOnMainThread {
//                    viewState.showLoading(false)
//                    profile?.let {
//                        it.isFriend = false
//                        viewState.showProfile(it)
//                    }
//                }
//            } catch (e: ApiException) {
//                runOnMainThread {
//                    viewState.showLoading(false)
//                    viewState.showError(e.message)
//                }
//            }
//        }

        MyProfilePresenter.myFriends.removeIf { it.personUid == profile.personUid }

        profile.isFriend = false

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