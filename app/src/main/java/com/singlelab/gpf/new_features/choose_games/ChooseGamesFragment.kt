package com.singlelab.gpf.new_features.choose_games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.model.event.EventType
import com.singlelab.gpf.model.event.FilterEvent
import com.singlelab.gpf.model.view.ToastType
import com.singlelab.gpf.new_features.games_model.GamePerson
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter.Companion.profile
import com.singlelab.net.exceptions.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_choose_games.button_apply
import kotlinx.android.synthetic.main.fragment_choose_games.card_view
import kotlinx.android.synthetic.main.fragment_choose_games.progress_bar
import kotlinx.android.synthetic.main.fragment_choose_games.title_types
import kotlinx.android.synthetic.main.view_grid_emoji.emoji_grid


@AndroidEntryPoint
class ChooseGamesFragment : BaseFragment() {

    var filterGame: FilterEvent? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("asd", "aszc")
        return inflater.inflate(R.layout.fragment_choose_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            filterGame = ChooseGamesFragmentArgs.fromBundle(
                it
            ).filterEvent
        }
        if (filterGame == null) filterGame = FilterEvent()
        showTypes(filterGame!!.selectedTypes)
        emoji_grid.children.forEachIndexed { index, view ->
            view.setOnClickListener {
                onClickType(index)
            }
        }

        button_apply.setOnClickListener {
            progress()
            filterGame?.let {
                applyFilter(it)
            }
        }
    }

    private fun progress() {
        requireView().isClickable = false
        card_view.alpha = 0.4f
        button_apply.alpha = 0.4f
        progress_bar.visibility = View.VISIBLE
    }

    private fun end() {
        requireView().isClickable = true
        progress_bar.visibility = View.GONE
        parentFragmentManager.popBackStack()
        findNavController().navigate(R.id.moveToMyProfileFragment)
    }

    private fun applyFilter(filter: FilterEvent) {
        val db = FirebaseFirestore.getInstance()

        profile!!.games = mutableListOf()
        filter.selectedTypes.forEach {
            when (it) {
                EventType.PARTY -> {
                    profile!!.games!!.add(GamePerson.DOTA)
                }

                EventType.CSGO -> {
                    profile!!.games!!.add(GamePerson.CSGO)
                }

                EventType.SPORT -> {
                    profile!!.games!!.add(GamePerson.OVERWATCH)
                }

                EventType.NATURE -> {
                    profile!!.games!!.add(GamePerson.VALORANT)
                }

                EventType.COMMUNICATION -> {
                    profile!!.games!!.add(GamePerson.PUBG)
                }

                EventType.GAME -> {
                    profile!!.games!!.add(GamePerson.DIABLO)
                }
            }
        }
        val docData = hashMapOf(
            "id" to profile!!.personUid,
            "login" to profile!!.login!!,
            "name" to profile!!.name,
            "description" to profile!!.description!!,
            "icon" to profile!!.imageContentUid!!,
            "city" to profile!!.cityName,
            "age" to profile!!.age,
            "recordMathCubes" to profile!!.personRecord2048,
            "recordFlappyCats" to profile!!.personRecordCats,
            "recordPianoTiles" to profile!!.personRecordPiano,
            "games" to profile!!.games,
            "friends" to profile!!.friends
        )

        try {
            db.collection("users").document(profile!!.personUid)
                .set(docData).addOnSuccessListener {
                    Log.d("ErrorGamesaVE", "0")
                    end()
                }
                .addOnFailureListener {
                    Log.d("ErrorGamesaVE", "2")
                    throw ApiException("")
                }
        } catch (e: ApiException) {
            Log.d("ErrorGamesaVE", "1")
            showError("Error to save games. Try again later!")
            end()
        }
    }

    private fun showError(str: String) {
        showSnackbar(
            str,
            ToastType.ERROR
        ) { }
    }

    private fun onClickType(type: Int) {
        filterGame ?: return
        if (filterGame!!.selectedTypes.find { it.id == type } != null) {
            removeType(type)
        } else {
            addType(type)
        }
    }

    private fun addType(type: Int) {
        filterGame?.selectedTypes?.apply {
            add(EventType.findById(type))
            showTypes(this)
        }
    }

    private fun removeType(type: Int) {
        filterGame?.selectedTypes?.apply {
            removeAll { it.id == type }
            showTypes(this)
        }
    }

    private fun showTypes(selectedTypes: List<EventType>) {
        if (selectedTypes.isEmpty() || selectedTypes.size == EventType.values().size) {
            title_types.text = getString(R.string.non_selected_all)
        } else {
            var text = getString(R.string.selected_types)
            selectedTypes.forEachIndexed { index, eventType ->
                text = "$text ${getString(eventType.titleRes)}"
                if (selectedTypes.size > 1 && index < selectedTypes.size - 1) {
                    text = "$text,"
                }
            }
            title_types.text = text
        }
        emoji_grid.children.forEachIndexed { index, view ->
            if (selectedTypes.find { it.id == index } != null) {
                view.alpha = 1f
            } else {
                view.alpha = 0.2f
            }
        }
    }
}