package com.singlelab.gpf.ui.edit_profile

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
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.model.profile.NewProfile
import com.singlelab.gpf.ui.cities.CitiesFragment
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.view.input.InputView
import com.singlelab.gpf.ui.view.input.OnInvalidStringsListener
import com.singlelab.net.exceptions.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit_profile.age
import kotlinx.android.synthetic.main.fragment_edit_profile.button_back
import kotlinx.android.synthetic.main.fragment_edit_profile.button_update
import kotlinx.android.synthetic.main.fragment_edit_profile.city
import kotlinx.android.synthetic.main.fragment_edit_profile.description
import kotlinx.android.synthetic.main.fragment_edit_profile.login
import kotlinx.android.synthetic.main.fragment_edit_profile.name
import kotlinx.android.synthetic.main.fragment_edit_profile.text_city
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment : BaseFragment(), EditProfileView, OnInvalidStringsListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
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

        text_city.setOnClickListener {
            toChooseCity()
        }
        button_back.setOnClickListener {
            findNavController().popBackStack()
        }
        button_update.setOnClickListener {
            button_update.isEnabled = false

            MyProfilePresenter.profile!!.login = login.getText()
            MyProfilePresenter.profile!!.name = name.getText()
            MyProfilePresenter.profile!!.age =
                age.getText().toInt()
            MyProfilePresenter.profile!!.cityName = city.getText()
            MyProfilePresenter.profile!!.description = description.getText()
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
            presenter.updateProfile()
        }
        parentFragmentManager.setFragmentResultListener(
            CitiesFragment.REQUEST_CITY,
            this,
            FragmentResultListener { requestKey, result ->
                onFragmentResult(requestKey, result)
            })
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