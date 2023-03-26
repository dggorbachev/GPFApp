package com.singlelab.gpf.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.singlelab.gpf.MainActivity
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.listeners.OnBackPressListener
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.view.tutorial.TutorialAdapter
import com.singlelab.gpf.util.maskPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_auth.*
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
        setListeners()
        initViewCode()
        initViewPhone()
        initTutorial()

        layout_name.setHint("Имя")
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
        button_send_code.isEnabled = !isShow
        button_auth.isEnabled = !isShow
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

    private fun setListeners() {

        button_back.setOnClickListener {
            showInputPhone()
            (activity as MainActivity).setBackPressListener(null)
        }

        button_send_code.setOnClickListener {
            MyProfilePresenter.profile!!.login = layout_login.getText()
            MyProfilePresenter.profile!!.name = layout_name.getText()
            MyProfilePresenter.profile!!.password = layout_password.getText()

            findNavController().navigate(R.id.action_auth_to_my_profile)
        }
//        button_auth.setOnClickListener {
//            Log.d("button_auth", "in pr")
//            MyProfilePresenter.profile!!.login="asd"
//            MyProfilePresenter.profile!!.name="New2"
//            findNavController().navigate(R.id.action_auth_to_my_profile)
////            onClickAuth()
//        }
        text_agreement.setOnClickListener {
            openBrowser(getString(R.string.url_agreement))
        }
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