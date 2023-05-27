package com.singlelab.gpf.ui.friends

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.MainActivity
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.base.OnlyForAuthFragments
import com.singlelab.gpf.base.listeners.OnPermissionListener
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.new_features.firebase.ChatFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.ui.chat.ChatPresenter
import com.singlelab.gpf.ui.chat.common.ChatOpeningInvocationType
import com.singlelab.gpf.ui.view.person.OnPersonItemClickListener
import com.singlelab.gpf.ui.view.person.PersonAdapter
import com.singlelab.gpf.util.ContactsUtil
import com.singlelab.gpf.util.TextInputDebounce
import com.singlelab.gpf.util.toShortPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_friends.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


@AndroidEntryPoint
class FriendsFragment : BaseFragment(), FriendsView, OnlyForAuthFragments,
    OnPersonItemClickListener, OnPermissionListener {

    @Inject
    lateinit var daggerPresenter: FriendsPresenter

    @InjectPresenter
    lateinit var presenter: FriendsPresenter

    @ProvidePresenter
    fun providePresenter() = daggerPresenter

    private var searchDebounce: TextInputDebounce? = null

    private var isSearchResults = false

    private var isContacts = false

    private var searchAdapter: PersonAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val isSearch = FriendsFragmentArgs.fromBundle(it).isSearch
            if (isSearch) {
                edit_text_search.requestFocus()
            }
            presenter.eventUid = FriendsFragmentArgs.fromBundle(it).eventUid
            presenter.participantIds = FriendsFragmentArgs.fromBundle(it).participantIds
            showSearch(presenter.eventUid.isNullOrEmpty())
        }
        recycler_friends.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            visibility = View.VISIBLE
        }
        edit_text_search.apply {
            setSingleLine()
            setHint(getString(R.string.search_friends))
            setStartDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_search))
        }
        searchDebounce = TextInputDebounce(
            editText = edit_text_search.getEditText(),
            isHandleEmptyString = true
        )
        searchDebounce!!.watch {
            if (isContacts && it.isEmpty()) {
                return@watch
            } else {
                isContacts = false
                presenter.search(it)
            }
        }
        button_import_contacts.setOnClickListener {
            onImportContactsClick()
        }
        title_invite_friends.setOnClickListener {
            shareText(Const.STORE_URL)
        }
    }

    override fun showFriends(friends: MutableList<Person>?) {
        isSearchResults = false
        recycler_search_results.visibility = View.GONE
        title_empty_search.visibility = View.GONE
        if (friends.isNullOrEmpty()) {
            showEmptyFriends()
        } else {
            title_empty_friends.visibility = View.GONE
            title_invite_friends.visibility = View.GONE
            recycler_friends.visibility = View.VISIBLE
            recycler_friends.adapter =
                PersonAdapter(
                    list = friends,
                    eventUid = presenter.eventUid,
                    participantIds = presenter.participantIds,
                    isInviting = true,
                    isAdministrator = false,
                    listener = this
                )
        }
    }

    override fun showSearchResult(searchResults: MutableList<Person>, page: Int) {
        isSearchResults = true
        recycler_friends.visibility = View.GONE
        title_empty_friends.visibility = View.GONE
        title_invite_friends.visibility = View.GONE
        title_empty_search.visibility = View.GONE
        if (searchResults.isNullOrEmpty() && page == 1) {
            title_empty_search.visibility = View.VISIBLE
            recycler_search_results.visibility = View.GONE
        } else if (searchResults.isNotEmpty()) {
            title_empty_search.visibility = View.GONE
            recycler_search_results.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                visibility = View.VISIBLE
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (layoutManager != null) {
                            val layoutManager = layoutManager as LinearLayoutManager
                            if (dy > 0 && !presenter.isLoading &&
                                (layoutManager.childCount + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.itemCount
                            ) {
                                presenter.search(
                                    edit_text_search.getText(),
                                    ++presenter.pageNumber
                                )
                            }
                        }
                    }
                })
            }
            if (searchAdapter == null || page == 1) {
                searchAdapter = PersonAdapter(
                    list = searchResults,
                    eventUid = presenter.eventUid,
                    participantIds = presenter.participantIds,
                    isInviting = true,
                    isAdministrator = false,
                    listener = this@FriendsFragment
                )
                recycler_search_results.adapter = searchAdapter
            } else {
                (recycler_search_results.adapter as PersonAdapter?)?.addData(searchResults)
            }
        }
    }

    override fun showContacts(persons: MutableList<Person>) {
        showSearchResult(persons, 1)
        isContacts = true
        edit_text_search.setText("")
    }

    override fun showEmptyFriends() {
        recycler_search_results.visibility = View.GONE
        title_empty_search.visibility = View.GONE
        recycler_friends.visibility = View.GONE
        title_empty_friends.visibility = View.VISIBLE
        title_invite_friends.visibility = View.VISIBLE
    }

    override fun showEmptyPersonsFromContacts() {
        showSnackbar(getString(R.string.empty_persons_from_contacts))
    }

    override fun onPersonClick(personUid: String) {
        findNavController().navigate(FriendsFragmentDirections.actionFriendsToPerson(personUid))
    }

    override fun onChatClick(personName: String, personUid: String) {

        val db = FirebaseFirestore.getInstance()

        db.collection("chats")
            .get()
            .addOnSuccessListener { chatDocs ->

                val auth = FirebaseAuth.getInstance()
                val currentUserId = auth.currentUser!!.uid
                var wasFound = false

                for (chatDocument in chatDocs) {
                    val chat = mapToObject(chatDocument.data, ChatFirebase::class)

                    if (chat.users.contains(personUid) && chat.users.contains(currentUserId) && !chat.isGroup) {
                        Log.d("ChatUid9", chat.id)
                        ChatPresenter.chatUid = chat.id
                        wasFound = true
                    }
                }

                if (!wasFound)
                    ChatPresenter.chatUid = "-1"

                findNavController().navigate(
                    FriendsFragmentDirections.actionFromFriendsToChat(
                        null,
                        ChatOpeningInvocationType.Person(
                            title = personName,
                            personUid = personUid
                        )
                    )
                )
            }
    }


    override fun onAddToFriends(personUid: String) {

        showChangeLangDialog(personUid)
    }

    fun showChangeLangDialog(personUid: String) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_group_chat_name, null)
        dialogBuilder.setView(dialogView)
        val edt = dialogView.findViewById<View>(R.id.etUserInput) as EditText
        dialogBuilder.setPositiveButton(
            "Готово"
        ) { dialog, whichButton ->
            val auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()
            val chatId = getRandomString(20)

            val chatDocData = hashMapOf(
                "id" to chatId,
                "image" to "https://www.marketingdirecto.com/wp-content/uploads/2012/11/focus-group.jpg",
                "isGroup" to true,
                "isLastMessageImage" to false,
                "lastMessageUserId" to auth.currentUser!!.uid,
                "lastMessageValue" to "Чат создан",
                "title" to edt.text.toString(),
                "users" to listOf<String>(personUid, auth.currentUser!!.uid)
            )

            db.collection("chats").document(chatId)
                .set(chatDocData)

        }
        dialogBuilder.setNegativeButton(
            "Отмена"
        ) { dialog, whichButton ->
        }
        val b = dialogBuilder.create()
        b.show()
    }

    override fun onInviteClick(personUid: String, eventUid: String) {
        presenter.invitePerson(personUid, eventUid, isSearchResults)
    }

    override fun onAcceptClick(personUid: String, eventUid: String) {
        presenter.invitePerson(personUid, eventUid, isSearchResults)
    }

    override fun onRejectClick(personUid: String, eventUid: String) {
    }

    override fun onRemoveFriendClick(personUid: String) {
        presenter.removeFriend(personUid, isSearchResults)
    }

    override fun onConfirmFriendClick(personUid: String) {
        presenter.confirmFriend(personUid, isSearchResults)
    }

    private fun showSearch(isShow: Boolean) {
        button_import_contacts.visibility = View.GONE
        edit_text_search.visibility = View.GONE
    }

    private fun onImportContactsClick() {
        (activity as MainActivity).checkContactsPermission()
    }

    override fun onLocationPermissionGranted() {
    }

    override fun onLocationPermissionDenied() {
    }

    override fun onContactsPermissionGranted() {
        activity?.contentResolver?.let {
            val contacts = ContactsUtil.getContacts(it)
            presenter.loadPersonsFromContacts(contacts.map { contact ->
                contact.phone.toShortPhone()
            })
        }
    }

    override fun onContactsPermissionDenied() {
        showSnackbar(getString(R.string.not_permission_contacts))
    }

    override fun onWriteExternalPermissionGranted() {
    }

    override fun onWriteExternalPermissionDenied() {
    }


    fun getRandomString(length: Int = 20): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}