package com.singlelab.gpf.ui.reg

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import com.google.firebase.auth.FirebaseAuth
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.listeners.OnActivityResultListener
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.model.view.ToastType
import com.singlelab.gpf.model.view.ValidationError
import com.singlelab.gpf.ui.cities.CitiesFragment
import com.singlelab.gpf.util.getBitmap
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_registration.button_registration
import kotlinx.android.synthetic.main.fragment_registration.button_upload_image
import kotlinx.android.synthetic.main.fragment_registration.card_image
import kotlinx.android.synthetic.main.fragment_registration.image
import kotlinx.android.synthetic.main.fragment_registration.layout_age
import kotlinx.android.synthetic.main.fragment_registration.layout_description
import kotlinx.android.synthetic.main.fragment_registration.layout_login1
import kotlinx.android.synthetic.main.fragment_registration.layout_mail
import kotlinx.android.synthetic.main.fragment_registration.layout_name
import kotlinx.android.synthetic.main.fragment_registration.layout_password
import kotlinx.android.synthetic.main.fragment_registration.layout_password_box
import kotlinx.android.synthetic.main.fragment_registration.text_city
import kotlinx.android.synthetic.main.fragment_registration.text_login
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(), RegistrationView, OnActivityResultListener {

    @Inject
    lateinit var daggerPresenter: RegistrationPresenter

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    private lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            initInputViews()
            setListeners()
        } else {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(R.id.my_profile)
        }
    }

    override fun onRegistration() {
        findNavController().popBackStack()
        findNavController().popBackStack()
        findNavController().navigate(R.id.my_profile)
    }

    override fun showName(name: String?) {
        layout_name.setText(name)
    }

    override fun showAge(age: Int) {
        layout_age.setText(age.toString())
    }

    override fun showDescription(description: String?) {
        layout_description.setText(description)
    }

    override fun showCity(cityName: String) {
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

    private fun initInputViews() {
        layout_mail.apply {
            setHint("Почта")
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@RegistrationFragment.view?.clearFocus()
                        layout_login1.requestEditTextFocus()
                    }
                }
                return@setOnEditorListener false
            }
            setSingleLine()
            setMaxLength(64)
            setWarning(getString(R.string.max_length, 64))
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.setMail(s.toString())
                }
            })
        }
        layout_login1.apply {
            setHint("Логин")
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@RegistrationFragment.view?.clearFocus()
                        layout_password.isFocusableInTouchMode = true
                        layout_password.requestFocus()
                    }
                }
                return@setOnEditorListener false
            }
            setSingleLine()
            setMaxLength(25)
            setWarning(getString(R.string.max_length, 25))
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.setLogin(s.toString())
                }
            })
        }
        layout_name.apply {
            setHint("Имя")
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@RegistrationFragment.view?.clearFocus()
                        layout_age.requestEditTextFocus()
                    }
                }
                return@setOnEditorListener false
            }
            setSingleLine()
            setMaxLength(25)
            setWarning(getString(R.string.max_length, 25))
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.setName(s.toString())
                }
            })
        }
        layout_age.apply {
            setHint(getString(R.string.age))
            setMaxLength(2)
            setInputType(InputType.TYPE_CLASS_NUMBER)
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@RegistrationFragment.view?.clearFocus()
                        text_city.requestEditTextFocus()
                    }
                }
                return@setOnEditorListener false
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    try {
                        val intAge = s.toString().toInt()
                        presenter.setAge(intAge)
                    } catch (e: Exception) {
                    }
                }
            })
        }
        text_city.apply {
            setHint("Город")
            disableLineBreaks()
            setMaxLines(5)
            setMaxLength(128)
            setWarning(getString(R.string.max_length, 128))
            setImeAction(EditorInfo.IME_ACTION_NEXT)
            setOnEditorListener {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@RegistrationFragment.view?.clearFocus()
                        layout_description.requestEditTextFocus()
                    }
                }
                return@setOnEditorListener false
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.setCity(s.toString())
                }
            })
        }
        layout_description.apply {
            setHint(getString(R.string.about_yourself))
            setLines(5)
            disableLineBreaks()
            setMaxLines(5)
            setMaxLength(128)
            setImeAction(EditorInfo.IME_ACTION_DONE)
            setWarning(getString(R.string.max_length, 128))
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.setDescription(s.toString())
                }
            })
        }
    }

    private fun setListeners() {
        layout_password_box.clearOnEndIconChangedListeners()
        layout_password_box.endIconMode = END_ICON_PASSWORD_TOGGLE
        fun setOnEditorListenerPass(function: (Int) -> Boolean) {
            layout_password.setOnEditorActionListener { _, actionId, _ ->
                function.invoke(actionId)
            }
        }
        layout_password.apply {
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setOnEditorListenerPass {
                if (it == EditorInfo.IME_ACTION_NEXT) {
                    runOnMainThread {
                        this@RegistrationFragment.view?.clearFocus()
                        layout_name.requestEditTextFocus()
                    }
                }
                return@setOnEditorListenerPass false
            }
        }
        text_login.setOnClickListener {
            findNavController().navigate(R.id.auth)
        }
        card_image.setOnClickListener {
            presenter.saveInputs()
            onClickChangeImage()
        }
        button_upload_image.setOnClickListener {
            presenter.saveInputs()
            onClickChangeImage()
        }
        button_registration.setOnClickListener {
            val validationError = validation()
            if (validationError == null) {
                try {
                    presenter.registration(
                        requireContext(),
                        requireActivity(),
                        layout_mail.getText(),
                        layout_password.text.toString(),
                        layout_login1.getText(),
                        text_city.getText(),
                        layout_name.getText(),
                        layout_age.getText().toInt(),
                        layout_description.getText()
                    )
                } catch (e: java.lang.Exception) {
                    showError(e.message ?: "")
                }
            } else {
                showError(getString(validationError.titleResId))
            }
        }
        parentFragmentManager.setFragmentResultListener(
            CitiesFragment.REQUEST_CITY,
            this,
            FragmentResultListener { requestKey, result ->
                onFragmentResult(requestKey, result)
            })
    }

    private fun showError(str: String) {
        runOnMainThread {
            showSnackbar(
                str,
                ToastType.ERROR
            ) { }
        }

    }

    private fun validation(): ValidationError? {
        return presenter.validation(
            layout_mail.getText(),
            layout_login1.getText(),
            layout_password.text.toString(),
            text_city.getText(),
            layout_name.getText(),
            layout_age.getText(),
            layout_description.getText()
        )
    }

    private fun onFragmentResult(requestKey: String, result: Bundle) {
        if (requestKey == CitiesFragment.REQUEST_CITY) {
            val city: City = result.getParcelable(CitiesFragment.RESULT_CITY) ?: return
            presenter.setCity(city)
        }
    }
}