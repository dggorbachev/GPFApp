package com.singlelab.gpf.ui.my_profile

import android.graphics.Bitmap
import android.util.Log
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.profile.Badge
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.PersonNotifications
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.my_profile.interactor.MyProfileInteractor
import com.singlelab.gpf.util.resize
import com.singlelab.gpf.util.toBase64
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.exceptions.NotConnectionException
import com.singlelab.net.model.auth.AuthData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MyProfilePresenter @Inject constructor(
    private var interactor: MyProfileInteractor,
    preferences: Preferences?
) : BasePresenter<MyProfileView>(preferences, interactor as BaseInteractor) {

    companion object {
        var profile: Profile? =
            Profile(
                "asd",
                null,
                "asd",
                "Профессиональный геймер",
                1,
                "Москва, Россия",
                18,
                "https://gflusercontent.gflclan.com/file/forums-prod/monthly_2018_11/0f837319a39026212f4597d6a57948ce2541dff2_full.jpg.3abe6b209c42299263c337a24d456310.jpg",
                false,
                arrayListOf()
            )

        var myFriends = mutableListOf(
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
            ),
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

    var friends: List<Person>? = null

    var badges: List<Badge>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        preferences?.let {
            if (preferences.isFirstLaunch()) {
                sendPushToken(preferences.getPushToken())
            }
            if (preferences.getNewYearPromoRewardEnabled()) {
                viewState.showNewYearView()
            }
        }

    }

    override fun onLoadedNotification(notifications: PersonNotifications) {
        super.onLoadedNotification(notifications)
        viewState.showNewBadge(notifications.hasNewBadges)
    }

    fun loadProfile(isFirstAttach: Boolean = false) {
        viewState.showLoading(isShow = true, withoutBackground = !isFirstAttach)
        invokeSuspend {
            try {
                withContext(Dispatchers.Main) {
                    viewState.showProfile(profile!!)
                    viewState.showLoading(isShow = false, withoutBackground = !isFirstAttach)
                }

                Log.d("isAnon", AuthData.isAnon.toString())
                if (!AuthData.isAnon) {
//                    showProfileFromCache(AuthData.uid, isFirstAttach)
//                    profile = interactor.loadProfile()
//                    profile?.let {
//                        interactor.saveProfile(it)
//                        preferences?.setCity(it.cityId, it.cityName)
//                        preferences?.setAge(it.age)
//                    }
//                    runOnMainThread {
//                        viewState.showLoading(isShow = false, withoutBackground = !isFirstAttach)
//                        if (profile != null) {
//                            viewState.showProfile(profile!!)
//                        } else {
//                            viewState.showError("Не удалось получить профиль")
//                        }
//                    }
                } else {
//                    runOnMainThread {
//                        preferences?.clearAuth()
//                        viewState.navigateToAuth()
//                    }
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = !isFirstAttach)
                    viewState.showError(
                        message = e.message,
                        withRetry = e is NotConnectionException,
                        callRetry = { loadProfile(isFirstAttach) }
                    )
                }
            }
        }
    }

    fun logout() {
        invokeSuspend {
            try {
                sendPushToken("")
                interactor.clearDatabase()
            } catch (e: ApiException) {
            }
            preferences?.clearAuth()
            runOnMainThread {
                viewState.navigateToAuth2()
                viewState.navigateToAuth()
            }
        }
    }

    fun updateImageProfile(image: Bitmap?) {
        image ?: return
        viewState.showLoading(isShow = true, withoutBackground = true)
        invokeSuspend {
            try {
                val imageStr = image.resize().toBase64()
                val miniImageStr = image.resize(200).toBase64()
                val uid = interactor.updateImageProfile(imageStr, miniImageStr)
                runOnMainThread {
                    viewState.loadImage(uid)
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

    fun loadFriends() {
        profile?.personUid?.let {
            Log.d(Const.LOG_TAG, "personUid = it")
            invokeSuspend {
                try {
//                    showFriendsFromCache()
//                    Log.d(Const.LOG_TAG, "loading")
//                    friends = interactor.loadFriends(it)
//                    Log.d(Const.LOG_TAG, "loaded")
//                    friends?.let {
//                        interactor.saveFriends(it)
//                    }
                    runOnMainThread {
                        friends = myFriends
                        this.viewState.onLoadedFriends(myFriends)
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    fun loadBadges() {
        profile?.personUid?.let {
            invokeSuspend {
                try {
                    badges = interactor.loadBadges(it)
                    runOnMainThread {
                        badges?.let {
                            viewState.onLoadedBadges(it)
                            updateNotifications()
                        }
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    private suspend fun showProfileFromCache(personUid: String?, isFirstAttach: Boolean) {
        personUid?.let {
            profile = interactor.loadProfileFromCache(personUid)
            profile?.let {
                delay(Const.MIN_DELAY_FOR_TRANSITION)
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = !isFirstAttach)
                    viewState.showProfile(it)
                }
            }
        }
    }

    private fun showFriendsFromCache() {
        invokeSuspend {
            val friends = interactor.loadFriendsFromCache()
            Log.d(Const.LOG_TAG, "friends = $friends")
            friends?.let {
                runOnMainThread {
                    viewState.onLoadedFriends(it)
                }
            }
        }
    }

    private fun sendPushToken(token: String?) {
        token ?: return
        invokeSuspend {
            try {
//                interactor.updatePushToken(token)
                preferences?.setFirstLaunch(false)
            } catch (e: ApiException) {
            }
        }
    }
}