package com.singlelab.gpf.new_features.firebase

import com.google.firebase.Timestamp
import java.util.*

data class MessageFirebase(
    val chatId: String,
    val id: String,
    val images: List<String>,
    val message: String,
    val senderId: String,
    val messageDate: Timestamp,
    val isAnyAttachments: Boolean
)