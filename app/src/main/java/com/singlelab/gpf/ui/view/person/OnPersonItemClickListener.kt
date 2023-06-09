package com.singlelab.gpf.ui.view.person

interface OnPersonItemClickListener {
    fun onPersonClick(personUid: String)

    fun onChatClick(personName: String, personUid: String)

    fun onAddToFriends(personUid: String)

    fun onInviteClick(personUid: String, eventUid: String)

    fun onAcceptClick(personUid: String, eventUid: String)

    fun onRejectClick(personUid: String, eventUid: String)

    fun onRemoveFriendClick(personUid: String)

    fun onConfirmFriendClick(personUid: String)
}