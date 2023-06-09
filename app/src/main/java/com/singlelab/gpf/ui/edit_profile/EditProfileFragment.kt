package com.singlelab.gpf.ui.edit_profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.listeners.OnActivityResultListener
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.model.profile.NewProfile
import com.singlelab.gpf.new_features.imgur.Upload
import com.singlelab.gpf.ui.cities.CitiesFragment
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.view.input.InputView
import com.singlelab.gpf.ui.view.input.OnInvalidStringsListener
import com.singlelab.gpf.util.getBitmap
import com.singlelab.gpf.util.resize
import com.singlelab.gpf.util.toBase64
import com.singlelab.net.exceptions.ApiException
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit_profile.age
import kotlinx.android.synthetic.main.fragment_edit_profile.button_back
import kotlinx.android.synthetic.main.fragment_edit_profile.button_update
import kotlinx.android.synthetic.main.fragment_edit_profile.button_upload_image
import kotlinx.android.synthetic.main.fragment_edit_profile.card_image
import kotlinx.android.synthetic.main.fragment_edit_profile.city
import kotlinx.android.synthetic.main.fragment_edit_profile.description
import kotlinx.android.synthetic.main.fragment_edit_profile.image
import kotlinx.android.synthetic.main.fragment_edit_profile.login
import kotlinx.android.synthetic.main.fragment_edit_profile.name
import kotlinx.android.synthetic.main.fragment_edit_profile.text_city
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment : BaseFragment(), EditProfileView, OnInvalidStringsListener,
    OnActivityResultListener {

    @Inject
    lateinit var daggerPresenter: EditProfilePresenter

    @InjectPresenter
    lateinit var presenter: EditProfilePresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun showImage(bitmap: Bitmap?) {
        image.setImageBitmap(bitmap)
    }

    override fun onActivityResultFragment(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val bitmap = result.uri.getBitmap(activity?.contentResolver)
                presenter.addImage(bitmap)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                showError(getString(R.string.error_pick_image))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (MyProfilePresenter.profile!!.imageContentUid != null) {
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                Glide.with(this).load(MyProfilePresenter.profile!!.imageContentUid).apply(options)
                    .into(image)
            }

            presenter.setProfile(EditProfileFragmentArgs.fromBundle(it).profile)
        }
        setListeners()
    }

    override fun showLogin(login: String?) {
        this.login.setText(login)
    }

    override fun showName(name: String?) {
        this.name.setText(name)
    }

    override fun showAge(age: Int) {
        this.age.setText(age.toString())
    }

    override fun showDescription(description: String?) {
        this.description.setText(description)
    }

    override fun showProfile(profile: NewProfile) {
        login.setText(profile.login)
        name.setText(profile.name)
        age.setText(profile.age.toString())
        city.setText(profile.city!!.cityName.toString())
        text_city.text = profile.city?.cityName
        description.setText(profile.description)
        initInputViews()
    }

    override fun onCompleteUpdate() {
        findNavController().popBackStack()
    }

    override fun onInvalidString(view: InputView) {
        view.setError(getString(R.string.login_hint))
    }

    override fun onCorrectString(view: InputView) {
        view.setWarning(getString(R.string.login_hint))
    }

    override fun showError(message: String, withRetry: Boolean, callRetry: () -> Unit) {
//        super.showError(message, withRetry, callRetry)
//        button_update.isEnabled = true
    }

    override fun showLoading(isShow: Boolean, withoutBackground: Boolean) {
        super.showLoading(isShow, withoutBackground)
        if (!isShow) {
            button_update.isEnabled = true
        }
    }

    private fun initInputViews() {
        login.apply {
            setHint(getString(R.string.login))
            setWarning(getString(R.string.login_hint))
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@EditProfileFragment.view?.clearFocus()
                        name.requestEditTextFocus()
                    }
                }
                return@setOnEditorListener false
            }
            setSingleLine()
            setMaxLength(25)
            val pattern = Regex(Const.REGEX_LOGIN)
            login.setInvalidStringsListener(pattern, this@EditProfileFragment)
        }
        name.apply {
            setHint(getString(R.string.name))
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@EditProfileFragment.view?.clearFocus()
                        age.requestEditTextFocus()
                    }
                }
                return@setOnEditorListener false
            }
            setSingleLine()
            setMaxLength(25)
            setWarning(getString(R.string.max_length, 25))
        }
        age.apply {
            setHint(getString(R.string.age))
            setMaxLength(2)
            setInputType(InputType.TYPE_CLASS_NUMBER)
            setImeAction(EditorInfo.IME_ACTION_DONE)
        }
        description.apply {
            setHint(getString(R.string.about_yourself))
            setLines(5)
            disableLineBreaks()
            setMaxLines(5)
            setMaxLength(128)
            setWarning(getString(R.string.max_length, 128))
        }
        city.apply {
            setHint("Город")
            setLines(5)
            disableLineBreaks()
            setMaxLines(5)
            setMaxLength(128)
            setWarning(getString(R.string.max_length, 128))
        }
    }

    private fun setListeners() {
        login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.setLogin(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.setName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        age.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val intAge = s.toString().toInt()
                    presenter.setAge(intAge)
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.setDescription(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        card_image.setOnClickListener {
            presenter.saveInputs()
            onClickChangeImage()
        }
        button_upload_image.setOnClickListener {
            presenter.saveInputs()
            onClickChangeImage()
        }
        text_city.setOnClickListener {
            toChooseCity()
        }
        button_back.setOnClickListener {
            findNavController().popBackStack()
        }
        button_update.setOnClickListener {
            button_update.isEnabled = false

            saveImage()
        }
        parentFragmentManager.setFragmentResultListener(
            CitiesFragment.REQUEST_CITY,
            this,
            FragmentResultListener { requestKey, result ->
                onFragmentResult(requestKey, result)
            })
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

    private fun saveProf(link: String) {
        MyProfilePresenter.profile!!.login = login.getText()
        MyProfilePresenter.profile!!.name = name.getText()
        MyProfilePresenter.profile!!.age =
            age.getText().toInt()
        MyProfilePresenter.profile!!.cityName = city.getText()
        MyProfilePresenter.profile!!.description = description.getText()
        MyProfilePresenter.profile!!.imageContentUid = link

        val docData = hashMapOf(
            "id" to MyProfilePresenter.profile!!.personUid,
            "login" to MyProfilePresenter.profile!!.login!!,
            "name" to MyProfilePresenter.profile!!.name,
            "description" to MyProfilePresenter.profile!!.description!!,
            "icon" to MyProfilePresenter.profile!!.imageContentUid!!,
            "city" to MyProfilePresenter.profile!!.cityName,
            "age" to MyProfilePresenter.profile!!.age,
            "icon" to MyProfilePresenter.profile!!.imageContentUid!!,
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
        presenter.updateProfile()
    }

    private fun saveImage() {
        try {
            val imageStr = EditProfilePresenter.image!!.resize().toBase64()


            loadImageToImgur(requireContext(), imageStr, login.getText()) { link ->

                try {

                    if (link == null) throw ApiException("Image not Uploaded. Check Internet Connection or try again later") else {
                        saveProf(link)
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        showLoading(false)
                        showError(e.message)
                    }
                }
            }


        } catch (e: ApiException) {
            runOnMainThread {
                showLoading(false)
                showError(e.message)
            }
        }
    }

    private fun onFragmentResult(requestKey: String, result: Bundle) {
        if (requestKey == CitiesFragment.REQUEST_CITY) {
            val city: City? = result.getParcelable(CitiesFragment.RESULT_CITY)
            presenter.setCity(city)
        }
    }

    private fun toChooseCity() {
        presenter.saveInputs()
        findNavController().navigate(EditProfileFragmentDirections.actionEditProfileToCities())
    }
}