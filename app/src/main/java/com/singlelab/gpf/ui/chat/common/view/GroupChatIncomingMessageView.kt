package com.singlelab.gpf.ui.chat.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.singlelab.gpf.R
import com.singlelab.gpf.ui.chat.common.ChatMessageViewExtensions
import com.singlelab.gpf.ui.chat.common.ChatMessageViewExtensions.Companion.MESSAGE_TEXT_MAX_WIDTH
import com.singlelab.gpf.ui.chat.common.GroupChatMessageItem
import com.singlelab.gpf.ui.chat.common.OnMessageAuthorClickEvent
import com.singlelab.gpf.util.dpToPx
import kotlinx.android.synthetic.main.group_incoming_message_item.view.*

class GroupChatIncomingMessageView
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
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        inflate(getContext(), R.layout.group_incoming_message_item, this)
    }

    fun setContent(
        messageItem: GroupChatMessageItem,
        clickEvent: OnMessageAuthorClickEvent?,
        listener: OnClickImageListener,
        messageListener: OnClickMessageListener
    ) {
        setText(messageItem)
        setAuthorView(messageItem, clickEvent)

        incomingMessageCloudView.setCloudView(messageItem.text, messageItem.images)
        incomingMessageDateView.setMessageDate(messageItem.text, messageItem.date)
        incomingMessageImageView.setImage(messageItem, listener)

        setOnLongClickListener {
            messageListener.onLongClick(messageItem.text)
            return@setOnLongClickListener true
        }
    }

    private fun setText(messageItem: GroupChatMessageItem) {
        incomingMessageView.setMessageText(messageItem.text)
        if (incomingMessageView.maxWidth != MESSAGE_TEXT_MAX_WIDTH.px) {
            maxMessageViewWidth = incomingMessageView.maxWidth
        }
        incomingMessageView.setMessageTextViewDimensions(messageItem.images, maxMessageViewWidth)
    }

    private fun setAuthorView(
        messageItem: GroupChatMessageItem,
        clickEvent: OnMessageAuthorClickEvent?
    ) {
        // Отображаем имя автора сообщения в групповом чате, тольео если сообщение не содержит картинки
        incomingMessageAuthorView.isVisible = messageItem.images.count { it.isNotEmpty() } == 0
        if (messageItem.images.count { it.isNotEmpty() } == 0) {
            incomingMessageAuthorView.text = messageItem.personName
        }

        if (messageItem.personPhoto.isNotEmpty()) {
            Glide.with(this)
                .load(messageItem.personPhoto)
                .transform(CenterCrop(), RoundedCorners(CORNER_RADIUS.dpToPx().toInt()))
                .into(incomingMessageAuthorPhotoView)
        }

        incomingMessageAuthorPhotoView.setOnClickListener { clickEvent?.invoke(messageItem.personUid) }
    }

    companion object {
        private const val CORNER_RADIUS = 10
    }
}