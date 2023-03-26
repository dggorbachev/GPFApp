package com.singlelab.gpf.ui.chats.common

data class ChatItem(
    val uid: String,
    val image: String,
    val title: String,
    val isGroup: Boolean,
    var lastMessage: String,
    val lastMessagePersonUid: String,
    var lastMessagePersonName: String,
    val isLastMessageImage: Boolean,
    var unreadMessagesCount: Int = 0
)