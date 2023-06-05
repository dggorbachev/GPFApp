package com.singlelab.gpf.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.MainActivity
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.listeners.OnBackPressListener
import com.singlelab.gpf.model.view.ToastType
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.new_features.games_model.GamePerson
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.view.tutorial.TutorialAdapter
import com.singlelab.gpf.util.maskPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_auth.button_auth
import kotlinx.android.synthetic.main.fragment_auth.button_back
import kotlinx.android.synthetic.main.fragment_auth.button_send_code
import kotlinx.android.synthetic.main.fragment_auth.layout_mail
import kotlinx.android.synthetic.main.fragment_auth.layout_password
import kotlinx.android.synthetic.main.fragment_auth.tab_layout
import kotlinx.android.synthetic.main.fragment_auth.text_agreement
import kotlinx.android.synthetic.main.fragment_auth.text_info
import kotlinx.android.synthetic.main.fragment_auth.view_pager_tutorial
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : BaseFragment(), AuthView, OnBackPressListener {

    @Inject
    lateinit var daggerPresenter: AuthPresenter

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun provideAuthPresenter() = daggerPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setListeners()
        initViewCode()
        initViewPhone()
        initTutorial()

        layout_mail.setHint("Почта")
    }

    override fun updateNewYearPromo(newYearPromoRewardEnabled: Boolean) {
//        super.updateNewYearPromo(newYearPromoRewardEnabled)
//        (view_pager_tutorial?.adapter as TutorialAdapter?)?.updateList(presenter.getTutorialList())
    }

    private fun initViewPhone() {
//        layout_phone.setHint("")
//        layout_phone.setStartDrawable(resources.getDrawable(R.drawable.ic_phone, context?.theme))
    }

    private fun initViewCode() {
//        layout_code.setHint(getString(R.string.enter_code))
//        layout_code.setMaxLines(1)
//        layout_code.setMaxLength(6)
//        layout_code.setInputType(InputType.TYPE_CLASS_NUMBER)
    }

    private fun showError(str: String) {
        showSnackbar(
            str,
            ToastType.ERROR
        ) { }
    }

    private fun initTutorial() {
        val tutorialList = presenter.getTutorialList()
        view_pager_tutorial?.apply {
            adapter = TutorialAdapter(tutorialList.toMutableList())
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            TabLayoutMediator(tab_layout, view_pager_tutorial) { _, _ -> }.attach()
        }
    }

    override fun showLoading(isShow: Boolean, withoutBackground: Boolean) {
        super.showLoading(isShow, withoutBackground)
//        button_send_code.isEnabled = !isShow
//        button_auth.isEnabled = !isShow
    }

    override fun onCodeSend(phone: String, isPushCode: Boolean) {
        (activity as MainActivity).setBackPressListener(this)
        text_info.visibility = View.VISIBLE
        if (isPushCode) {
            text_info.text = getString(R.string.push_code_send)
        } else {
            text_info.text = getString(R.string.sms_code_send, phone.maskPhone())
        }
        button_back.visibility = View.VISIBLE
//        layout_code.visibility = View.VISIBLE
//        layout_code.setText("")
        button_auth.visibility = View.VISIBLE

//        layout_phone.visibility = View.GONE
        button_send_code.visibility = View.GONE
    }

    override fun toProfile() {
//        findNavController().popBackStack()
//        findNavController().navigate(R.id.my_profile)
    }

    override fun toRegistration() {
        (activity as MainActivity).setBackPressListener(null)
        findNavController().navigate(AuthFragmentDirections.actionAuthToRegistration())
    }

    override fun onBackPressed() {
        showInputPhone()
        (activity as MainActivity).setBackPressListener(null)
    }

    private lateinit var auth: FirebaseAuth

    private fun setListeners() {

        button_back.setOnClickListener {
            showInputPhone()
            (activity as MainActivity).setBackPressListener(null)
        }

        button_send_code.setOnClickListener {
            auth.signInWithEmailAndPassword(layout_mail.getText(),
                layout_password.getText().toString()
            )
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("MyAPPLog", "signInWithEmail:success")
                        val user = auth.currentUser
                        val db = FirebaseFirestore.getInstance()

                        // get user from cloudstore
                        db.collection("users")
                            .get()
                            .addOnSuccessListener { result ->
                                var found = false
                                for (document in result) {
                                    if (document.id == user!!.uid) {
                                        showLoading(false, true)
                                        launchProfile(
                                            mapToObject(
                                                document.data,
                                                UserFirebase::class
                                            )
                                        )
                                        found = true
                                    }
                                }
                                // if no id on cloudstore -> error
                                if (!found) {
                                    showError("Authentication failed. Check fields!")
                                }
                            }
                            .addOnFailureListener { exception ->
                                showError("Authentication failed. Check fields!")
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("MyAPPLog", "signInWithEmail:failure", task.exception)
                        showError("Authentication failed. Check fields!")
                    }
                }
            MyProfilePresenter.profile!!.password = layout_password.getText().toString()
        }
        text_agreement.setOnClickListener {
            openBrowser(getString(R.string.url_agreement))
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
            personUid = user.id
            friends = user.friends as MutableList<String>
            likeTo = user.likeTo as MutableList<String>
            newFriends = user.newFriends
            age = user.age.toInt()
            imageContentUid = user.icon
            personRecord2048 = user.recordMathCubes.toInt()
            personRecordCats = user.recordFlappyCats.toInt()
            personRecordPiano = user.recordPianoTiles.toInt()
            personRecordTetris = user.recordTetris.toInt()
        }
        findNavController().navigate(R.id.action_auth_to_my_profile)
    }

    private fun onClickAuth() {
        hideKeyboard()
        (activity as MainActivity).setBackPressListener(null)
//        presenter.onClickAuth(layout_code.getText())
    }

    private fun onClickSendCode() {
        hideKeyboard()
//        if (layout_phone.isValid()) {
//            context?.let {
//                presenter.onClickSendCode(
//                    layout_phone.getText().toShortPhone(),
//                    NotificationManagerCompat.from(it).areNotificationsEnabled()
//                )
//            }
//        }
    }

    private fun showInputPhone() {
        button_back.visibility = View.INVISIBLE
        text_info.visibility = View.INVISIBLE
//        layout_code.visibility = View.GONE
        button_auth.visibility = View.GONE

//        layout_phone.visibility = View.VISIBLE
        button_send_code.visibility = View.VISIBLE
    }
}