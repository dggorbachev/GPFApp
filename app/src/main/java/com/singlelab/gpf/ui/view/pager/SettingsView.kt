package com.singlelab.gpf.ui.view.pager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.singlelab.gpf.MobileNavigationDirections
import com.singlelab.gpf.R
import com.singlelab.gpf.model.event.EventType
import com.singlelab.gpf.model.event.FilterEvent
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.new_features.games_model.GamePerson
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.view.pager.listener.OnSettingsClickListener
import kotlinx.android.synthetic.main.view_settings.view.about_developer
import kotlinx.android.synthetic.main.view_settings.view.agreement
import kotlinx.android.synthetic.main.view_settings.view.battlenet
import kotlinx.android.synthetic.main.view_settings.view.epic
import kotlinx.android.synthetic.main.view_settings.view.exit
import kotlinx.android.synthetic.main.view_settings.view.feedback
import kotlinx.android.synthetic.main.view_settings.view.games
import kotlinx.android.synthetic.main.view_settings.view.instagram
import kotlinx.android.synthetic.main.view_settings.view.origin
import kotlinx.android.synthetic.main.view_settings.view.steam


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
            FirebaseAuth.getInstance().signOut()
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
            MyProfilePresenter.myFriends = mutableListOf(
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

            settingsClickListener?.onLogoutClick()
        }

        games.setOnClickListener {
            if (MyProfilePresenter.profile!!.games != null) {
                val types = mutableListOf<EventType>()
                MyProfilePresenter.profile!!.games!!.forEach {
                    when (it) {
                        GamePerson.DOTA -> types.add(EventType.PARTY)
                        GamePerson.CSGO -> types.add(EventType.CSGO)
                        GamePerson.OVERWATCH -> types.add(EventType.SPORT)
                        GamePerson.VALORANT -> types.add(EventType.NATURE)
                        GamePerson.PUBG -> types.add(EventType.COMMUNICATION)
                        GamePerson.DIABLO -> types.add(EventType.GAME)
                    }
                }
                findNavController().navigate(
                    MobileNavigationDirections.moveToChooseGamesFragment(
                        FilterEvent(selectedTypes = types)
                    )
                )
            } else {
                findNavController().navigate(
                    MobileNavigationDirections.moveToChooseGamesFragment(
                        null
                    )
                )
            }
        }

//        steam.setOnClickListener {
//            val browserIntent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://store.steampowered.com/login/?l=russian")
//            )
//            startActivity(context, browserIntent, null)
//        }

        battlenet.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://eu.battle.net/login/ru/?ref=https://oauth.battle.net/authorize?response_type%3Dcode%26client_id%3D057adb2af62a4d59904f74754838c4c8%26scope%3Daccount.full%2520commerce.virtualcurrency.full%26state%3DRptQcgtyAbhlrUMafncMdO1vMHrkEPSCf-vQTUFd3JY%253D%26redirect_uri%3Dhttps://account.battle.net/callback/oauth2/code/account-settings&app=oauth")
            )
            startActivity(context, browserIntent, null)
        }

        origin.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://signin.ea.com/p/juno/login?execution=e486618532s1&initref=https%3A%2F%2Faccounts.ea.com%3A443%2Fconnect%2Fauth%3Finitref_replay%3Dfalse%26display%3DjunoWeb%252Flogin%26response_type%3Dcode%26redirect_uri%3Dhttps%253A%252F%252Fwww.ea.com%252Flogin_check%26locale%3Dru_RU%26client_id%3DEADOTCOM-WEB-SERVER")
            )
            startActivity(context, browserIntent, null)
        }

        epic.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.epicgames.com/id/login"))
            startActivity(context, browserIntent, null)
        }
    }
}