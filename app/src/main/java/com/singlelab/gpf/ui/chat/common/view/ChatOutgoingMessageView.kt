package com.singlelab.gpf.ui.chat.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.singlelab.gpf.R
import com.singlelab.gpf.ui.chat.common.ChatMessageItem
import com.singlelab.gpf.ui.chat.common.ChatMessageViewExtensions
import com.singlelab.gpf.ui.chat.common.ChatMessageViewExtensions.Companion.MESSAGE_TEXT_MAX_WIDTH
import kotlinx.android.synthetic.main.outgoing_message_item.view.*

class ChatOutgoingMessageView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(
    context,
    attrs,
    defStyleAttr
), ChatMessageViewExtensions {

    private var maxMessageViewWidth: Int = 0

    init {
        layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gravity = Gravity.END
        inflate(getContext(), R.layout.outgoing_message_item, this)
    }

    fun setContent(
        messageItem: ChatMessageItem, listener: OnClickImageListener,
        messageListener: OnClickMessageListener
    ) {
        setText(messageItem)
        setPendingMessageProgress(messageItem.status)

        outgoingMessageCloudView.setCloudView(messageItem.text, messageItem.images)
        outgoingMessageImageView.setImage(messageItem, listener)
        outgoingMessageDateView.setMessageDate(messageItem.text, messageItem.date)

        setOnLongClickListener {
            messageListener.onLongClick(messageItem.text)
            return@setOnLongClickListener true
        }
    }

    private fun setText(messageItem: ChatMessageItem) {
        outgoingMessageView.setMessageText(messageItem.text)
        if (outgoingMessageView.maxWidth != MESSAGE_TEXT_MAX_WIDTH.px) {
            maxMessageViewWidth = outgoingMessageView.maxWidth
        }
        outgoingMessageView.setMessageTextViewDimensions(messageItem.images, maxMessageViewWidth)
    }

    private fun setPendingMessageProgress(messageStatus: ChatMessageItem.Status) {
        // Отображаем прогресс бар для сообщения, которое ожидает отправки
        outgoingMessageImageProgressView.isVisible = messageStatus == ChatMessageItem.Status.PENDING
    }
}