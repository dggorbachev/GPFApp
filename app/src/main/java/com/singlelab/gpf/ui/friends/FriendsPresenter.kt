package com.singlelab.gpf.ui.friends

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.new_features.firebase.UserFirebase
import com.singlelab.gpf.new_features.firebase.mapToObject
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.friends.interactor.FriendsInteractor
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.model.auth.AuthData
import com.singlelab.net.model.person.SearchPersonRequest
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class FriendsPresenter @Inject constructor(
    private var interactor: FriendsInteractor,
    preferences: Preferences?
) : BasePresenter<FriendsView>(preferences, interactor as BaseInteractor) {

    var pageNumber = 1

    var eventUid: String? = null

    var isLoading = false

    var participantIds: Array<String>? = null

    private var friends: MutableList<Person>? = null

    private var searchResults: MutableList<Person>? = null

    private var pageSize = Const.PAGE_SIZE

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFriends()
    }

    fun search(searchStr: String, page: Int = 1) {

    }

    fun addToFriends(personUid: String) {
        viewState.showLoading(isShow = true, withoutBackground = true)
        invokeSuspend {
            try {
                interactor.addToFriends(personUid)
                runOnMainThread {
                    searchResults?.find {
                        it.personUid == personUid
                    }?.isFriend = true
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    searchResults?.let {
                        viewState.showSearchResult(it, pageNumber)
                    }
                    updateNotifications()
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    viewState.showError(e.message)
                }
            }
        }
    }

    fun invitePerson(personUid: String, eventUid: String, isSearchResult: Boolean) {
        viewState.showLoading(isShow = true, withoutBackground = true)
        invokeSuspend {
            try {
                interactor.invitePerson(personUid, eventUid)
                runOnMainThread {
                    if (isSearchResult) {
                        searchResults?.find {
                            it.personUid == personUid
                        }?.isInvited = true
                        searchResults?.let {
                            viewState.showSearchResult(it, pageNumber)
                        }
                    } else {
                        friends?.find {
                            it.personUid == personUid
                        }?.isInvited = true
                        friends?.removeAll { it.friendshipApprovalRequired }
                        viewState.showFriends(friends)
                    }
                    viewState.showLoading(isShow = false, withoutBackground = true)
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    viewState.showError(e.message)
                }
            }
        }
    }

    fun removeFriend(personUid: String, isSearchResult: Boolean) {
        viewState.showLoading(isShow = true, withoutBackground = true)
        invokeSuspend {
            try {
                interactor.removeFriend(personUid)
                runOnMainThread {
                    if (isSearchResult) {
                        searchResults?.removeAll {
                            it.personUid == personUid
                        }
                        searchResults?.let {
                            viewState.showSearchResult(it, pageNumber)
                        }
                    } else {
                        friends?.removeAll {
                            it.personUid == personUid
                        }
                        viewState.showFriends(friends)
                    }
                    viewState.showLoading(isShow = false, withoutBackground = true)
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    viewState.showError(e.message)
                }
            }
        }
    }

    fun confirmFriend(personUid: String, isSearchResult: Boolean) {
        viewState.showLoading(isShow = true, withoutBackground = true)
        invokeSuspend {
            try {
                interactor.confirmFriend(personUid)
                runOnMainThread {
                    if (isSearchResult) {
                        searchResults?.find {
                            it.personUid == personUid
                        }?.friendshipApprovalRequired = false
                        searchResults?.let {
                            viewState.showSearchResult(it, pageNumber)
                        }
                    } else {
                        friends?.find {
                            it.personUid == personUid
                        }?.friendshipApprovalRequired = false
                        viewState.showFriends(friends)
                    }
                    updateNotifications()
                    viewState.showLoading(isShow = false, withoutBackground = true)
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    viewState.showError(e.message)
                }
            }
        }
    }

    fun loadPersonsFromContacts(phones: List<String>) {
        viewState.showLoading(isShow = true, withoutBackground = true)
        invokeSuspend {
            try {
                searchResults = interactor.getPersonsFromContacts(phones)?.toMutableList()
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    if (searchResults.isNullOrEmpty()) {
                        viewState.showEmptyPersonsFromContacts()
                    } else {
                        viewState.showContacts(searchResults!!)
                    }
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    viewState.showError(e.message)
                }
            }
        }
    }

    private fun loadFriends() {
        viewState.showLoading(isShow = true, withoutBackground = true)

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        lateinit var currentUser: UserFirebase
        val friends = mutableListOf<UserFirebase>()

        db.collection("users")
            .get().addOnSuccessListener { result ->

                for (userDoc in result) {
                    val user = mapToObject(userDoc.data, UserFirebase::class)

                    if (auth.currentUser!!.uid == user.id) {
                        currentUser = user
                    }
                }

                for (userDoc in result) {
                    val user = mapToObject(userDoc.data, UserFirebase::class)

                    if (currentUser.friends.contains(user.id)) {
                        friends.add(user)
                    }
                }
                runOnMainThread {
                    viewState.showLoading(isShow = false, withoutBackground = true)
                    viewState.showFriends(friends.toPersonMutableList())
                }

//                friends = interactor.getFriends(uid)?.toMutableList()
//                if (eventUid != null) {
//                    friends?.removeAll { it.friendshipApprovalRequired }
//                }
//                runOnMainThread {
//                    viewState.showLoading(isShow = false, withoutBackground = true)
//                    viewState.showFriends(friends)
//                }
            }
    }

    private fun UserFirebase.toPerson(): Person =
        Person(
            personUid = this.id,
            name = this.name,
            login = this.login,
            description = this.description,
            age = this.age.toInt(),
            cityName = this.city,
            imageContentUid = this.icon,
            isFriend = false,
            isInvited = false,
            friendshipApprovalRequired = false
        )

    private fun  MutableList<UserFirebase>.toPersonMutableList(): MutableList<Person> = map{
        it.toPerson()
    }.toMutableList()
}