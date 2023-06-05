package com.singlelab.gpf.ui.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.OnlyForAuthFragments
import com.singlelab.gpf.ui.chat.ChatPresenter
import com.singlelab.gpf.ui.chat.common.ChatOpeningInvocationType
import com.singlelab.gpf.ui.chat.common.ChatsItemDecorator
import com.singlelab.gpf.ui.chats.common.ChatItem
import com.singlelab.gpf.ui.chats.common.ChatsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chats.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class ChatsFragment : BaseFragment(), ChatsView, OnlyForAuthFragments {
    @Inject
    lateinit var daggerPresenter: ChatsPresenter

    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    private val chatsAdapter: ChatsAdapter by lazy { ChatsAdapter { navigateToChat(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_chats, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatsTitleView.text = getString(R.string.chats_title)
        initViews()
    }

    override fun showChats(chats: List<ChatItem>) {
        chatsView.visibility = View.VISIBLE
        emptyChatsView.visibility = View.GONE
        chatsAdapter.setChats(chats)
        chatsAdapter.notifyDataSetChanged()
    }

    override fun showEmptyChats() {
        chatsView.visibility = View.GONE
        emptyChatsView.visibility = View.VISIBLE
    }

    private fun navigateToChat(chat: ChatItem) {
        Log.d("ChatUid3", chat.uid)
        ChatPresenter.chatUid = chat.uid
        findNavController().navigate(
            ChatsFragmentDirections.actionFromChatsToChat(
                null,
                ChatOpeningInvocationType.Common(chat.title, chat.uid, chat.isGroup)
            )
        )
    }

    private fun initViews() {
        addGroupChatButton.setOnClickListener {
            findNavController().navigate(R.id.action_from_chats_to_friends)
        }

        chatsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        chatsView.adapter = chatsAdapter
        ContextCompat.getDrawable(requireContext(), R.drawable.chats_item_divider)
            ?.let { chatsView.addItemDecoration(ChatsItemDecorator(it)) }
    }

    override fun onResume() {
        super.onResume()

        if (chatsView.adapter != null)
            chatsView.adapter!!.notifyDataSetChanged()
    }
}