package com.singlelab.gpf.ui.games

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.interactive_games.flappy_cats.FlappyCatsHomeActivity
import com.singlelab.gpf.interactive_games.game_2048.PrimaryMenuActivity
import com.singlelab.gpf.interactive_games.piano_tiles.view.PianoTilesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_games.*

@AndroidEntryPoint
class GamesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        game.setOnClickListener {
            startActivity(Intent(requireContext(), PrimaryMenuActivity::class.java))
        }
        game1.setOnClickListener {
            startActivity(Intent(requireContext(), FlappyCatsHomeActivity::class.java))
        }
        game2.setOnClickListener {
            startActivity(Intent(requireContext(), PianoTilesActivity::class.java))
        }

        records.setOnClickListener {
            findNavController().navigate(R.id.action_games_to_records)
        }
    }
}