package com.singlelab.gpf.ui.view.pager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.singlelab.gpf.R
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.view.pager.listener.OnSettingsClickListener
import kotlinx.android.synthetic.main.view_settings.view.*


class SettingsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), PagerTabView {

    private var settingsClickListener: OnSettingsClickListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_settings, this, true)
    }

    override fun getTitle() = context.getString(R.string.tab_settings)

    override fun getView() = this

    fun setSettingsListener(listener: OnSettingsClickListener) {
        this.settingsClickListener = listener
        instagram.setOnClickListener {
            settingsClickListener?.onInstagramClick()
        }
        feedback.setOnClickListener {
            settingsClickListener?.onFeedbackClick()
        }
        agreement.setOnClickListener {
            settingsClickListener?.onAgreementClick()
        }
        about_developer.setOnClickListener {
            settingsClickListener?.onAboutDeveloperClick()
        }
        exit.setOnClickListener {
            MyProfilePresenter.profile = Profile(
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

            settingsClickListener?.onLogoutClick()
        }
    }
}