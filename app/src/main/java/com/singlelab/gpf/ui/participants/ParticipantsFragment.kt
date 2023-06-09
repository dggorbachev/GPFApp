package com.singlelab.gpf.ui.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.OnlyForAuthFragments
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.ui.view.person.OnPersonItemClickListener
import com.singlelab.gpf.ui.view.person.PersonAdapter
import com.singlelab.net.model.auth.AuthData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_participants.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


@AndroidEntryPoint
class ParticipantsFragment : BaseFragment(), ParticipantsView, OnlyForAuthFragments,
    OnPersonItemClickListener {

    @Inject
    lateinit var daggerPresenter: ParticipantsPresenter

    @InjectPresenter
    lateinit var presenter: ParticipantsPresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_participants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            ParticipantsFragmentArgs.fromBundle(it).let { args ->
                presenter.withNotApproved = args.withNotApproved
                presenter.eventUid = args.eventUid
                presenter.participants = args.participants.toMutableList()
                presenter.isAdministrator = args.isAdministrator
            }
        }
        recycler_participants.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            visibility = View.VISIBLE
            presenter.participants?.let { list ->
                adapter = PersonAdapter(
                    list = list,
                    eventUid = presenter.eventUid,
                    isInviting = false,
                    isAdministrator = presenter.isAdministrator,
                    listener = this@ParticipantsFragment
                )
            }
        }
    }

    override fun onPersonClick(personUid: String) {
        if (personUid == AuthData.uid) {
            findNavController().navigate(ParticipantsFragmentDirections.actionParticipantsToMyProfile())
        } else {
            findNavController().navigate(
                ParticipantsFragmentDirections.actionParticipantsToPerson(
                    personUid
                )
            )
        }
    }

    override fun onChatClick(personName: String, personUid: String) {
    }

    override fun onAddToFriends(personUid: String) {
    }

    override fun onInviteClick(personUid: String, eventUid: String) {
    }

    override fun onAcceptClick(personUid: String, eventUid: String) {
        presenter.approvePerson(personUid, eventUid)
    }

    override fun onRejectClick(personUid: String, eventUid: String) {
        presenter.rejectPerson(personUid, eventUid)
    }

    override fun onRemoveFriendClick(personUid: String) {
    }

    override fun onConfirmFriendClick(personUid: String) {
    }

    override fun showParticipants(list: List<Person>) {
        (recycler_participants.adapter as PersonAdapter).setData(list)
    }
}