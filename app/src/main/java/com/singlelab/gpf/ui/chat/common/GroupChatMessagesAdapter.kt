package com.singlelab.gpf.ui.chat.common

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.singlelab.gpf.ui.chat.common.view.GroupChatIncomingMessageView
import com.singlelab.gpf.ui.chat.common.view.OnClickImageListener
import com.singlelab.gpf.ui.chat.common.view.OnClickMessageListener

class GroupChatMessagesAdapter(
    clickEvent: OnMessageAuthorClickEvent,
    listener: OnClickImageListener,
    messageListener: OnClickMessageListener
) : ChatMessagesAdapter(
    Type.GROUP,
    clickEvent,
    listener,
    messageListener
) {

    override fun setMessages(newMessages: List<ChatMessageItem>) {
        val syncedMessages = newMessages.syncLast()
        val diffResult =
            DiffUtil.calculateDiff(GroupChatMessagesDiffCallback(messages, syncedMessages), false)
        messages.clear()
        messages.addAll(syncedMessages)
        diffResult.dispatchUpdatesTo(this)
    }

    class GroupIncomingMessageViewHolder(
        view: View,
        private val clickEvent: OnMessageAuthorClickEvent?
    ) : ChatMessageViewHolder(view) {
        override fun bind(
            messageItem: ChatMessageItem,
            listener: OnClickImageListener,
            messageListener: OnClickMessageListener
        ) {
            if (messageItem !is GroupChatMessageItem) return
            if (itemView !is GroupChatIncomingMessageView) return
            itemView.setContent(messageItem, clickEvent, listener, messageListener)
        }
    }

    private class GroupChatMessagesDiffCallback(
        private val oldMessages: List<ChatMessageItem>,
        private val newMessages: List<ChatMessageItem>
    ) : ChatMessagesDiffCallback(oldMessages, newMessages) {

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldMessage = oldMessages[oldItemPosition] as? GroupChatMessageItem ?: return false
            val newMessage = newMessages[newItemPosition] as? GroupChatMessageItem ?: return false

            return super.areContentsTheSame(oldItemPosition, newItemPosition) &&
                    oldMessage.personName == newMessage.personName &&
                    oldMessage.personPhoto == newMessage.personPhoto
        }
    }
}