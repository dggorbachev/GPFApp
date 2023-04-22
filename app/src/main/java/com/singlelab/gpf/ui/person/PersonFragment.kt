package com.singlelab.gpf.ui.person

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.model.view.ToastType
import com.singlelab.gpf.ui.chat.ChatPresenter
import com.singlelab.gpf.ui.chat.common.ChatOpeningInvocationType
import com.singlelab.gpf.util.PluralsUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_person.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : BaseFragment(), PersonView {

    @Inject
    lateinit var daggerPresenter: PersonPresenter

    @InjectPresenter
    lateinit var presenter: PersonPresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            presenter.loadProfile(PersonFragmentArgs.fromBundle(it).personUid)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
            showImage(profile.imageContentUid)
            image.setOnClickListener {
                showFullScreenImage(profile.imageContentUid!!)
            }
        }
        if (profile.isFriend) {
            button_action_friend.text = getString(R.string.remove_from_friends)
        } else {
            button_action_friend.text = getString(R.string.add_to_friends)
        }

        button_action_friend.setOnClickListener {
            if (profile.isFriend) {
                presenter.removeFromFriends(profile)
            } else {
                presenter.addToFriends(profile)
            }
        }
        button_chat.setOnClickListener {
            ChatPresenter.chatUid = profile.personUid
            toChat(profile.name, profile.personUid)
        }
        button_back.setOnClickListener {
            findNavController().popBackStack()
        }
        button_report_person.setOnClickListener {
            showReport()
        }
    }

    override fun showSuccessReport() {
        hideKeyboard()
        showSnackbar(getString(R.string.report_send), ToastType.SUCCESS)
    }

    private fun showReport() {
        showEditTextDialog(
            title = getString(R.string.enter_reason_report),
            emptyText = getString(R.string.empty_reason),
            callback = {
                presenter.sendReport(it)
            })
    }

    private fun toChat(title: String, personUid: String) {
        findNavController().navigate(
            PersonFragmentDirections.actionPersonToChat(
                null,
                ChatOpeningInvocationType.Person(
                    title = title,
                    personUid = personUid
                )
            )
        )
    }

    private fun showFullScreenImage(imageContentUid: String) {
        context?.let {
            val links = listOf(imageContentUid)
            findNavController().navigate(PersonFragmentDirections.actionPersonToImageSlider(links.toTypedArray()))
        }
    }

    private fun showImage(imageUid: String?) {
        imageUid?.let {
            Glide.with(this)
                .load(imageUid)
                .thumbnail(0.1f)
                .into(image)
        }
    }
}