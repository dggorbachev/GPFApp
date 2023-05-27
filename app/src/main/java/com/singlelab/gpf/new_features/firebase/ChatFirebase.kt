package com.singlelab.gpf.new_features.firebase

data class ChatFirebase(
    val id: String,
    val image: String,
    val isGroup: Boolean,
    val isLastMessageImage: Boolean,
    val lastMessageUserId: String,
    val lastMessageValue: String,
    val title: String,
    val users: List<String>
)