package com.singlelab.gpf.ui.view.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.ui.chats.ChatsPresenter
import com.singlelab.gpf.ui.friends.FriendsPresenter
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter

class PersonAdapter(
    private val list: MutableList<Person>,
    private val eventUid: String? = null,
    private val participantIds: Array<String>? = null,
    private val isInviting: Boolean = false,
    private val isAdministrator: Boolean = false,
    private val listener: OnPersonItemClickListener
) : RecyclerView.Adapter<PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PersonViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = list[position]
        val removeFromChat = FriendsPresenter.currentChat.users.any{ it == person.personUid }

        val currentUserSet = person.personUid == MyProfilePresenter.profile!!.personUid

        holder.bind(person, eventUid, participantIds, isInviting, isAdministrator, listener, removeFromChat, currentUserSet)
    }

    override fun getItemCount(): Int = list.size

    fun addData(list: List<Person>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(list: List<Person>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}