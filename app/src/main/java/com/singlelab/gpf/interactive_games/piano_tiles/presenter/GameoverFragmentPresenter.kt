package com.singlelab.gpf.interactive_games.piano_tiles.presenter

import com.singlelab.gpf.interactive_games.piano_tiles.DBHandler
import com.singlelab.gpf.interactive_games.piano_tiles.model.PianoGameScore
import com.singlelab.gpf.interactive_games.piano_tiles.view.CustomToast
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GameoverFragmentPresenter(
    var score: Int,
    var ui: IGameoverFragment,
    var db: DBHandler,
    var toast: CustomToast
) {
    var isSaved = false
    fun loadData() {
        ui.setScore(score)
    }

    fun saveScore(playerName: String?) {
        MyProfilePresenter.profile!!.personRecordPiano = score

        // TODO SCORE
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strDate = dateFormat.format(date);
//        Score score = new Score(0, this.score, playerName, strDate);
//        this.db.addRecord(score);
//        this.toast.setText("Saved!");
//        this.toast.show();
//        this.isSaved = true;
    }

    fun backToMenu(playerName: String?) {
        if (!isSaved) {
            val date = Calendar.getInstance().time
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
            val strDate = dateFormat.format(date)
            val pianoGameScore = PianoGameScore(0, score, playerName, strDate)
            db.addRecord(pianoGameScore)
        }
        ui.changePage(0)
    }

    fun playAgain(playerName: String?) {
        if (!isSaved) {
            val date = Calendar.getInstance().time
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
            val strDate = dateFormat.format(date)
            val pianoGameScore = PianoGameScore(0, score, playerName, strDate)
            db.addRecord(pianoGameScore)
        }
        ui.changePage(1)
    }

    interface IGameoverFragment {
        fun setScore(score: Int)
        fun changePage(page: Int)
    }
}