package com.singlelab.gpf.ui.chats.common

import com.singlelab.gpf.database.entity.Chat
import com.singlelab.net.model.chat.ChatResponse

fun List<ChatResponse>.toDbEntities(): List<Chat> =
    mapNotNull { it.toDbEntity() }

fun ChatResponse.toDbEntity(): Chat? {
    return Chat(
        uid = chatUid ?: return null,
        title = name ?: "",
        isGroup = isGroupChat ?: false,
        image = (if (isGroupChat == true) eventImageUid else personImageUid) ?: "",
        lastMessage = lastMessage?.messageContent ?: "",
        lastMessagePersonUid = lastMessage?.personUid ?: "",
        lastMessagePersonName = lastMessage?.personName ?: "",
        isLastMessageImage = false,
        unreadMessagesCount = unreadMessagesCount
    )
}

fun List<Chat>.toUiEntities(): List<ChatItem> =
    map { it.toDbEntity() }

fun Chat.toDbEntity(): ChatItem =
    ChatItem(
        uid = uid,
        title = title,
        image = image,
        isGroup = isGroup,
        lastMessage = lastMessage,
        lastMessagePersonUid = lastMessagePersonUid,
        lastMessagePersonName = lastMessagePersonName,
        isLastMessageImage = isLastMessageImage,
        unreadMessagesCount = unreadMessagesCount
    )