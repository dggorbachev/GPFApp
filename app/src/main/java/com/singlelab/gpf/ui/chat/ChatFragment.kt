package com.singlelab.gpf.ui.chat

import android.app.Activity
import android.content.*
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.OnlyForAuthFragments
import com.singlelab.gpf.base.listeners.OnActivityResultListener
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.Const.SELECT_IMAGE_REQUEST_CODE
import com.singlelab.gpf.model.view.ToastType
import com.singlelab.gpf.new_features.imgur.Upload
import com.singlelab.gpf.ui.chat.ChatPresenter.Companion.selectedChat
import com.singlelab.gpf.ui.chat.common.*
import com.singlelab.gpf.ui.chat.common.ChatMessageItem.Companion.PENDING_MESSAGE_UID
import com.singlelab.gpf.ui.chat.common.ChatMessageItem.Status
import com.singlelab.gpf.ui.chat.common.ChatMessageItem.Type
import com.singlelab.gpf.ui.chat.common.view.OnClickImageListener
import com.singlelab.gpf.ui.chat.common.view.OnClickMessageListener
import com.singlelab.gpf.ui.friends.FriendsPresenter
import com.singlelab.gpf.util.getBitmap
import com.singlelab.gpf.util.resize
import com.singlelab.gpf.util.toBase64
import com.singlelab.net.exceptions.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chats.addGroupChatButton
import kotlinx.android.synthetic.main.item_badge.image
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment : BaseFragment(), ChatView, OnlyForAuthFragments, OnActivityResultListener {
    @Inject
    lateinit var daggerPresenter: ChatPresenter

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    private lateinit var chatMessagesAdapter: ChatMessagesAdapter

    private var chatType: ChatOpeningInvocationType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Const.LOG_TAG, "ChatFragment: onCreateView")
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(Const.LOG_TAG, "ChatFragment: onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            chatType = ChatFragmentArgs.fromBundle(it).chatType ?: return
            initViews()
            presenter.showChat(chatType)
        }
    }

    override fun onStart() {
        Log.d(Const.LOG_TAG, "ChatFragment: onStart")
        super.onStart()
    }

    override fun onStop() {
        Log.d(Const.LOG_TAG, "ChatFragment: onStop")
        super.onStop()
    }

    override fun onPause() {
        Log.d(Const.LOG_TAG, "ChatFragment: onPause")
        super.onPause()
    }

    override fun onResume() {
        Log.d(Const.LOG_TAG, "ChatFragment: onResume")
        super.onResume()
    }

    override fun showChat(messages: List<ChatMessageItem>, page: Int) {
        emptyChatView.visibility = View.GONE
        chatMessagesAdapter.setMessages(messages)
        if (page == 1) {
            chatView.scrollToPosition(chatMessagesAdapter.itemCount - 1)
        }
    }

    override fun showEmptyChat() {
        emptyChatView.visibility = View.VISIBLE
    }

    override fun showNewMessage(message: ChatMessageItem) {
        emptyChatView.visibility = View.GONE
        chatMessagesAdapter.addMessage(message)
        chatView.scrollToPosition(chatMessagesAdapter.itemCount - 1)
    }

    override fun enableMessageSending(isEnabled: Boolean) {
        sendMessageView.isEnabled = isEnabled
        attachmentMessageView.isEnabled = isEnabled
    }

    override fun onActivityResultFragment(requestCode: Int, resultCode: Int, data: Intent?) {
        attachmentMessageView.isEnabled = true
        if (ImagePicker.shouldHandleResult(
                requestCode,
                resultCode,
                data,
                SELECT_IMAGE_REQUEST_CODE
            )
        ) {
            val images = ImagePicker.getImages(data)
                .mapNotNull { it.uri.getBitmap(activity?.contentResolver) }
            if (resultCode == Activity.RESULT_OK && images.isNotEmpty()) {
                sendMessage(images)
            } else {
                showError(getString(R.string.error_pick_image))
            }
        }
    }

    override fun navigateToPerson(personUid: String) {
        findNavController().navigate(ChatFragmentDirections.actionChatToPerson(personUid))
    }

    override fun showMuteDialog(isMuted: Boolean) {
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        presenter.muteChat()
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
        val text = if (isMuted) R.string.on_push else R.string.mute_push
        showDialog(
            title = getString(text),
            listener = dialogClickListener
        )
    }

    override fun showTitle(title: String?) {
        if (title != null) {
            chatTitleView.text = title
        }
    }

    override fun showAddMember(isMuted: Boolean) {
        if (isMuted)
            addUserToGroupChatView.visibility = View.VISIBLE
        else
            addUserToGroupChatView.visibility = View.INVISIBLE
    }

    override fun navigateToEvent(eventUid: String) {
        findNavController().navigate(ChatFragmentDirections.actionChatToEvent(eventUid))
    }

    private fun initViews() {
        val listener = object : OnClickImageListener {
            override fun onClickImage(imageUids: List<String>) {
                navigateToImages(imageUids)
            }
        }
        val messageListener = object : OnClickMessageListener {
            override fun onLongClick(text: String) {
                showMessageAction(text)
            }
        }
        val clickEvent = object : OnMessageAuthorClickEvent {
            override fun invoke(personUid: String) {
                navigateToPerson(personUid)
            }
        }
        chatMessagesAdapter = if (chatType != null && chatType!!.isGroup) {
            GroupChatMessagesAdapter(clickEvent, listener, messageListener)
        } else {
            PrivateChatMessagesAdapter(listener, messageListener)
        }
        chatView.adapter = chatMessagesAdapter
        chatView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
                stackFromEnd = true;
            }
        chatView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                (chatView.layoutManager as LinearLayoutManager?)?.let { layoutManager ->
                    if (dy < 0 && presenter.isNeedLoading() && layoutManager.findFirstVisibleItemPosition() == 0) {
                        presenter.showChat(
                            type = presenter.chatSettings.chatType,
                            page = ++presenter.page
                        )
                    }
                }
            }
        })
        chatTitleView.setOnClickListener {
            presenter.onChatTitleClick()
        }
        chatView.addItemDecoration(SpaceDivider(16))
        chatBackView.setOnClickListener { findNavController().popBackStack() }
        sendMessageView.setOnClickListener { sendMessage() }

        attachmentMessageView.setOnClickListener {
            addAttachment()
            attachmentMessageView.isEnabled = false
        }

        addUserToGroupChatView.setOnClickListener {
            FriendsPresenter.addToChat = true
            FriendsPresenter.currentChat = selectedChat
            findNavController().navigate(ChatFragmentDirections.actionFromChatToFriends())
        }
    }

    private fun navigateToImages(imageUids: List<String>) {
        val action = ChatFragmentDirections.actionChatToImageSlider(imageUids.toTypedArray())
        findNavController().navigate(action)
    }

    private fun showMessageAction(text: String) {
        showListDialog(
            getString(R.string.choose_action),
            arrayOf(
                getString(R.string.copy_message)
            ), DialogInterface.OnClickListener { _, which ->
                when (which) {
                    0 -> copy(text)
                }
            }
        )
    }

    private fun copy(text: String) {
        val clipboard: ClipboardManager? =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("Сообщение", text)
        clipboard?.setPrimaryClip(clip)
        showSnackbar(getString(R.string.text_copied), ToastType.SUCCESS)
    }

    private fun sendMessage(images: List<Bitmap> = emptyList()) {
        var currentText = messageInputView.text.toString().trim()

        if (images.isNotEmpty()) {

            images.forEach { image ->

                val imageStr = image.resize().toBase64()

                loadImageToImgur(requireContext(), imageStr, "usvatriun") { link ->

                    try {

                        if (link == null) throw ApiException("Image not Uploaded. Check Internet Connection or try again later")
                        else {
                            ChatPresenter.linksToImages.add(link)
                            showPendingMessage(currentText, images)
                            presenter.sendMessage("Вложение", ChatPresenter.linksToImages)
                        }
                    } catch (e: ApiException) {
                        runOnMainThread {
                            showError(e.message)
                        }
                    }
                }
            }


        } else if (currentText.isNotEmpty()) {
            showPendingMessage(currentText, images)
            presenter.sendMessage(currentText, ChatPresenter.linksToImages)
            messageInputView.setText("")
        }
    }

    fun loadImageToImgur(
        context: Context,
        imageData: String,
        login: String,
        callback: (link: String?) -> Unit
    ) {
        val body = JSONObject()
        body.put("image", imageData)
        body.put("title", login + "_title")
        body.put("description", login + "_description")
        body.put("type", "base64")
        val paramsPictures = HashMap<String, String>()
        paramsPictures["Authorization"] = "Client-ID e10417823faf68d"
        paramsPictures["content-type"] = "application/json"
        Upload.getRequest(
            context,
            paramsPictures,
            "https://api.imgur.com/3/upload",
            { callback(it) },
            Request.Method.POST,
            body.toString(),
            "Image Uploaded!",
            "Image not Uploaded. Check Internet Connection or try again later!"
        )
    }

    private fun addAttachment() {
        onClickAddImages()
    }

    private fun showPendingMessage(text: String, images: List<Bitmap>) {
        val pendingMessage = if (chatType != null && chatType!!.isGroup) {
            GroupChatMessageItem(
                uid = PENDING_MESSAGE_UID,
                text = text,
                type = Type.OUTGOING,
                images = images.map { it.toString() },
                status = Status.PENDING,
                date = "",
                personUid = "",
                personPhoto = "",
                personName = ""
            )
        } else {
            PrivateChatMessageItem(
                uid = PENDING_MESSAGE_UID,
                text = text,
                type = Type.OUTGOING,
                images = images.map { it.toString() },
                status = Status.PENDING,
                date = ""
            )
        }
        showNewMessage(pendingMessage)
    }
}