package com.singlelab.gpf.ui.edit_profile


import android.graphics.Bitmap
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.model.profile.NewProfile
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.model.view.ValidationError
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.edit_profile.interactor.EditProfileInteractor
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class EditProfilePresenter @Inject constructor(
    private val interactor: EditProfileInteractor,
    preferences: Preferences?
) : BasePresenter<EditProfileView>(preferences, interactor as BaseInteractor) {

    companion object{
        var image: Bitmap? = null
    }
    private var newProfile = NewProfile()

    fun setProfile(profile: Profile) {
        newProfile = NewProfile(profile)
        viewState.showProfile(newProfile)
    }

    fun setCity(city: City?) {
        city?.let {
            newProfile.city = city
            viewState.showProfile(newProfile)
        }
    }

    fun addImage(bitmap: Bitmap?) {
        image = bitmap
        viewState.showImage(bitmap)
    }

    fun setLogin(login: String) {
        newProfile.login = login
    }

    fun setName(name: String) {
        newProfile.name = name
    }

    fun setAge(age: Int) {
        newProfile.age = age
    }

    fun setDescription(description: String) {
        newProfile.description = description
    }

    fun saveInputs() {
        viewState.showLogin(newProfile.login)
        viewState.showName(newProfile.name)
        if (newProfile.age != null) {
            viewState.showAge(newProfile.age!!)
        }
        viewState.showDescription(newProfile.description)
    }

    fun updateProfile() {
//        val validationError = validation()
//        if (validationError != null) {
//            viewState.showError(validationError.titleResId)
//        } else {
//            viewState.showLoading(isShow = true, withoutBackground = true)
//            invokeSuspend {
//                try {
//                    interactor.updateProfile(newProfile)
//                    runOnMainThread {
//                        viewState.showLoading(isShow = false, withoutBackground = true)
        viewState.onCompleteUpdate()
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

    private fun validation(): ValidationError? {
        return if (!newProfile.login.isNullOrEmpty() && !newProfile.login!!.matches(Const.REGEX_LOGIN.toRegex())) {
            ValidationError.INVALID_LOGIN
        } else if (newProfile.login != null && newProfile.login!!.isEmpty() ||
            newProfile.name != null && newProfile.name!!.isEmpty() ||
            newProfile.age != null && (newProfile.age == null) ||
            newProfile.description != null && newProfile.description!!.isEmpty()
        )
            ValidationError.UNFILLED_FIELDS
        else {
            null
        }
    }
}