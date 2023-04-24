package com.singlelab.gpf.ui.my_profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.listeners.OnActivityResultListener
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.profile.Badge
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.model.view.PagerTab
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.new_features.games_model.GamePerson
import com.singlelab.gpf.ui.view.pager.FriendsView
import com.singlelab.gpf.ui.view.pager.PagerAdapter
import com.singlelab.gpf.ui.view.pager.PagerTabView
import com.singlelab.gpf.ui.view.pager.SettingsView
import com.singlelab.gpf.ui.view.pager.listener.OnFriendsClickListener
import com.singlelab.gpf.ui.view.pager.listener.OnSettingsClickListener
import com.singlelab.gpf.ui.view.person_short.OnPersonShortClickListener
import com.singlelab.gpf.util.PluralsUtil
import com.singlelab.gpf.util.getBitmap
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_my_profile.button_edit_profile
import kotlinx.android.synthetic.main.fragment_my_profile.city
import kotlinx.android.synthetic.main.fragment_my_profile.description
import kotlinx.android.synthetic.main.fragment_my_profile.image
import kotlinx.android.synthetic.main.fragment_my_profile.login
import kotlinx.android.synthetic.main.fragment_my_profile.name
import kotlinx.android.synthetic.main.fragment_my_profile.tab_layout
import kotlinx.android.synthetic.main.fragment_my_profile.view_pager
import kotlinx.android.synthetic.main.fragment_my_profile.view_shape
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


@AndroidEntryPoint
class MyProfileFragment : BaseFragment(), MyProfileView, OnActivityResultListener,
    OnPersonShortClickListener, OnSettingsClickListener, OnFriendsClickListener {

    @Inject
    lateinit var daggerPresenter: MyProfilePresenter

    @InjectPresenter
    lateinit var presenter: MyProfilePresenter

    private var settingsView: SettingsView? = null

    private var friendsView: FriendsView? = null

    private val callbackBackPressed: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }

    @ProvidePresenter
    fun provideMyProfilePresenter() = daggerPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {

            findNavController().popBackStack()
            findNavController().navigate(R.id.registration)
        } else {
            if (MyProfilePresenter.profile == null || MyProfilePresenter.profile!!.login == null) {
                showLoading(true, true)
                reload()
            } else {
                presenter.loadProfile(MyProfilePresenter.profile == null)
            }
        }
//        view.findViewById<ImageView>(R.id.image)
//            .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_my_profile_to_auth))
//        if (AuthData.isAnon) {
//            navigateToAuth()
//        } else {
//        }
    }

    private fun reload() {
        //reload currentUser
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val db = FirebaseFirestore.getInstance()

                // get user from cloudstore
                db.collection("users")
                    .get()
                    .addOnSuccessListener { result ->
                        var found = false
                        for (document in result) {
                            if (document.id == auth.currentUser!!.uid) {
                                showLoading(false, true)
                                launchProfile(mapToObject(document.data, UserFirebase::class))
                                found = true
                            }
                        }
                        // if no id on cloudstore -> error
                        if (!found) {
                            fail()
                        }
                    }
                    .addOnFailureListener { exception ->
                        fail()
                    }
            } else {
                fail()
            }
        }
    }

    private fun fail() {
        Toast.makeText(
            context,
            "Failed to load data. Try again later!.",
            Toast.LENGTH_SHORT
        ).show()
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
            age = user.age.toInt()
            imageContentUid = user.icon
            personRecord2048 = user.recordMathCubes.toInt()
            personRecordCats = user.recordFlappyCats.toInt()
            personRecordPiano = user.recordPianoTiles.toInt()
        }
        presenter.loadProfile(MyProfilePresenter.profile == null)
    }

    override fun showProfile(profile: Profile) {
        val age = PluralsUtil.getString(
            profile.age,
            "год",
            "года",
            "года",
            "года",
            "лет"
        )
        name.text = "${profile.name}, $age"
        login.text = "@${profile.login}"
        description.text = profile.description
        city.text = profile.cityName
        if (!profile.imageContentUid.isNullOrEmpty()) {
            loadImage(profile.imageContentUid)
        }
        image.setOnClickListener {
            if (profile.imageContentUid == null) {
                onClickChangeImage()
            } else {
                onClickImage(profile.imageContentUid!!)
            }
        }
        button_edit_profile.setOnClickListener {
            MyProfilePresenter.profile?.let {
                findNavController().navigate(
                    MyProfileFragmentDirections.actionMyProfileToEditProfile(MyProfilePresenter.profile!!)
                )
            }
        }
        initViewPager()
    }

    override fun onLoadedFriends(friends: List<Person>?) {
        friendsView?.let {
            it.setFriends(friends)
            (view_pager.adapter as PagerAdapter?)?.updateFriendsView(it)
        }
    }

    override fun onLoadedBadges(badges: List<Badge>) {
//        badgesView?.let {
//            it.setBadges(badges)
//            (view_pager.adapter as PagerAdapter?)?.updateBadgesView(it)
//        }
    }

    override fun showNewBadge(hasNewBadges: Boolean) {
        if (hasNewBadges) {
            val badge = tab_layout.getTabAt(1)?.orCreateBadge
            badge?.backgroundColor =
                ContextCompat.getColor(requireContext(), R.color.colorNotification)
        } else {
            tab_layout.getTabAt(1)?.removeBadge()
        }
    }

    override fun showLoadingFriends(isShow: Boolean) {
        friendsView?.showLoading(isShow)
    }

    override fun showNewYearView() {
        context?.let {
            view_shape.background =
                ContextCompat.getDrawable(it, R.drawable.shape_profile_new_year)
        }
    }

    override fun navigateToAuth() {
//        findNavController().popBackStack()
//        findNavController().navigate(R.id.auth)
    }

    override fun navigateToAuth2() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.registration)
    }

    override fun loadImage(imageUid: String?) {
        imageUid?.let {
            Glide.with(this)
                .load(imageUid)
                .thumbnail(0.1f)
                .into(image)
        }
    }

    override fun onActivityResultFragment(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val bitmap = result.uri.getBitmap(activity?.contentResolver)
//                presenter.updateImageProfile(bitmap)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                showError(getString(R.string.error_pick_image))
            }
        }
    }

    override fun onPersonClick(personUid: String) {
        findNavController().navigate(
            MyProfileFragmentDirections.actionMyProfileToPerson(
                personUid
            )
        )
    }

    override fun onInstagramClick() {
        openBrowser(getString(R.string.url_instagram))
    }

    override fun onFeedbackClick() {
        findNavController().navigate(MyProfileFragmentDirections.actionMyProfileToFeedback())
    }

    override fun onAgreementClick() {
        openBrowser(getString(R.string.url_agreement))
    }

    override fun onAboutDeveloperClick() {
        openBrowser(getString(R.string.url_about_developer))
    }

    override fun onLogoutClick() {
        presenter.logout()
    }

    override fun onSearchFriendsClick() {
        toFriends(true)
    }

    override fun onNewFriendsClick() {
        toFriends(true)
    }

    override fun onInviteFriendsClick() {
        shareText(Const.STORE_URL)
    }

    fun onBackPressed() {
        callbackBackPressed.remove()
    }

    private fun initViewPager() {
        context?.let {
            friendsView = FriendsView(it)
            friendsView?.setFriendsListener(this, this)


            settingsView = SettingsView(it)
            settingsView?.setSettingsListener(this)
        }
        val views = mutableListOf<PagerTabView>(friendsView!!, settingsView!!)
        view_pager.apply {
            adapter = PagerAdapter(views)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = views[position].getTitle()
            view_pager.setCurrentItem(tab.position, true)
        }.attach()
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    PagerTab.FRIENDS.position -> {
                        friendsView?.showLoading(true)
                        presenter.loadFriends()
                    }
                }
            }
        })
    }

    private fun onClickImage(imageContentUid: String) {
        showFullScreenImage(imageContentUid)
//        showListDialog(
//            getString(R.string.choose_action),
//            arrayOf(
//                getString(R.string.show_image),
//                getString(R.string.change_image)
//            ), DialogInterface.OnClickListener { _, which ->
//                when (which) {
//                    0 -> showFullScreenImage(imageContentUid)
//                    1 -> onClickChangeImage()
//                }
//            }
//        )
    }

    private fun showFullScreenImage(imageContentUid: String) {
        context?.let {
            val links = listOf(imageContentUid)
            findNavController().navigate(
                MyProfileFragmentDirections.actionMyProfileToImageSlider(
                    links.toTypedArray()
                )
            )
        }
    }

    private fun toFriends(isSearch: Boolean = false) {
        val action = MyProfileFragmentDirections.actionMyProfileToFriends()
        action.isSearch = isSearch
        findNavController().navigate(action)
    }
}