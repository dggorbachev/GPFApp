package com.singlelab.gpf.ui.reg

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.Patterns
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.model.view.ValidationError
import com.singlelab.gpf.new_features.imgur.Upload
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.reg.interactor.RegistrationInteractor
import com.singlelab.gpf.util.resize
import com.singlelab.gpf.util.toBase64
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.model.person.ProfileRequest
import moxy.InjectViewState
import org.json.JSONObject
import javax.inject.Inject


@InjectViewState
class RegistrationPresenter @Inject constructor(
    private val interactor: RegistrationInteractor,
    preferences: Preferences?
) : BasePresenter<RegistrationView>(preferences, interactor as BaseInteractor) {

    private val profileRequest = ProfileRequest()

    private var city: City? = null

    private var image: Bitmap? = null

    fun registration(
        context: Context,
        activity: FragmentActivity,
        mail: String,
        password: String,
        login: String,
        city: String,
        name: String,
        age: Int,
        description: String
    ) {
        viewState.showLoading(true)
        invokeSuspend {
            val imageStr = image!!.resize().toBase64()
            val miniImageStr = image!!.resize(200).toBase64()

            try {


                loadImageToImgur(context, imageStr, login) { link ->

                    if (link == null) throw ApiException("Image not Uploaded. Check Internet Connection or try again later") else {
                        register(
                            activity,
                            mail,
                            password,
                            login,
                            city,
                            name,
                            age,
                            description,
                            link
                        )

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

    fun register(
        activity: FragmentActivity,
        mail: String,
        password: String,
        login: String,
        city: String,
        name: String,
        age: Int,
        description: String,
        link: String
    ) {
        val profile = ProfileRequest(
            mail = mail,
            login = login,
            name = name,
            age = age,
            description = description,
            city = city,
            image = "imageStr",
            miniImage = "miniImageStr"
        )
        profile.image = link
        try {
            invokeSuspend {
                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MyAPPLog", "createUserWithEmail:success")
                            val user = auth.currentUser
                            saveUser(user, profile)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("MyAPPLog", "createUserWithEmail:failure", task.exception)
                            throw ApiException("Failed to create account. Try again later!")
                        }
                    }


            }
        } catch (e: ApiException) {
            runOnMainThread {
                viewState.showLoading(false)
                viewState.showError(e.message)
            }
        }
    }

    fun saveUser(user: FirebaseUser?, profile: ProfileRequest) {
        try {
            if (user == null)
                throw ApiException("User not found. Try again later!")

            val docData = hashMapOf(
                "id" to user.uid,
                "login" to profile.login!!,
                "name" to profile.name!!,
                "description" to profile.description!!,
                "icon" to profile.image!!,
                "city" to profile.city!!,
                "age" to profile.age!!,
                "recordMathCubes" to 0,
                "recordFlappyCats" to 0,
                "recordPianoTiles" to 0,
                "games" to arrayListOf<String>("DOTA"),
                "friends" to arrayListOf<String>()
            )

            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid)
                .set(docData)
                .addOnSuccessListener {
                    // interactor.registration(profile)
                    preferences?.setAnon(false)
                    runOnMainThread {
                        MyProfilePresenter.profile!!.apply {
                            login = profile.login
                            name = profile.name!!
                            description = profile.description
                            cityName = profile.city!!
                            personUid = user.uid
                            age = profile.age!!
                            imageContentUid = profile.image!!
                            personRecord2048 = 0
                            personRecordCats = 0
                            personRecordPiano = 0
                        }
                        viewState.onRegistration()
                    }
                    Log.d("MyAPPLog", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener {
                    throw ApiException("Failed to create account. Try again later!")
                }
        } catch (e: ApiException) {
            runOnMainThread {
                viewState.showLoading(false)
                viewState.showError(e.message)
            }
        }
    }

    fun loadImageToImgur(
        context: Context,
        imageData: String,
        login: String,
        callback: (link: String?) -> Unit
    ) {
        val body = JSONObject()
        body.put("image", imageData)
        body.put("title", login + "_title")
        body.put("description", login + "_description")
        body.put("type", "base64")
        val paramsPictures = HashMap<String, String>()
        paramsPictures["Authorization"] = "Client-ID e10417823faf68d"
        paramsPictures["content-type"] = "application/json"
        Upload.getRequest(
            context,
            paramsPictures,
            "https://api.imgur.com/3/upload",
            { callback(it) },
            Request.Method.POST,
            body.toString(),
            "Image Uploaded!",
            "Image not Uploaded. Check Internet Connection or try again later!"
        )
    }

    fun setCity(city: City) {
        this.city = city
        viewState.showCity(city.cityName)
    }

    fun addImage(bitmap: Bitmap?) {
        this.image = bitmap
        viewState.showImage(bitmap)
    }

    fun validation(
        mail: String?,
        login: String?,
        password: String?,
        city: String?,
        name: String?,
        age: String?,
        description: String?
    ): ValidationError? {
        return when {

            !mail.isValidEmail() -> ValidationError.EMPTY_MAIL
            login.isNullOrEmpty() -> ValidationError.EMPTY_LOGIN
            password.isNullOrEmpty() || password.length <= 6 -> ValidationError.EMPTY_PASSWORD
            city.isNullOrEmpty() -> ValidationError.EMPTY_CITY
            name.isNullOrEmpty() -> ValidationError.EMPTY_NAME
            description.isNullOrEmpty() -> ValidationError.EMPTY_DESCRIPTION
            age.isNullOrEmpty() -> ValidationError.EMPTY_AGE
            !age.isDigitsOnly() -> ValidationError.INVALID_AGE
            image == null -> ValidationError.EMPTY_PHOTO
            else -> null
        }
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun setName(name: String) {
        profileRequest.name = name
    }

    fun setLogin(login: String) {
        profileRequest.login = login
    }

    fun setMail(mail: String) {
        profileRequest.mail = mail
    }

    fun setAge(age: Int) {
        profileRequest.age = age
    }

    fun setDescription(description: String) {
        profileRequest.description = description
    }

    fun setCity(city: String) {
        profileRequest.city = city
    }

    fun saveInputs() {
        viewState.showName(profileRequest.name)
        if (profileRequest.age != null) {
            viewState.showAge(profileRequest.age!!)
        }
        viewState.showDescription(profileRequest.description)
    }
}