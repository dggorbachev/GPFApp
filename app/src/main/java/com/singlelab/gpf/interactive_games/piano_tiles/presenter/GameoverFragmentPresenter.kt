package com.singlelab.gpf.interactive_games.piano_tiles.presenter

import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.interactive_games.piano_tiles.DBHandler
import com.singlelab.gpf.interactive_games.piano_tiles.model.PianoGameScore
import com.singlelab.gpf.interactive_games.piano_tiles.view.CustomToast
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.net.exceptions.ApiException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

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
        if (MyProfilePresenter.profile!!.personRecordPiano < score) {
            MyProfilePresenter.profile!!.personRecordPiano = score
            val docData = hashMapOf(
                "id" to MyProfilePresenter.profile!!.personUid,
                "login" to MyProfilePresenter.profile!!.login!!,
                "name" to MyProfilePresenter.profile!!.name,
                "description" to MyProfilePresenter.profile!!.description!!,
                "icon" to MyProfilePresenter.profile!!.imageContentUid!!,
                "city" to MyProfilePresenter.profile!!.cityName,
                "age" to MyProfilePresenter.profile!!.age,
                "recordMathCubes" to MyProfilePresenter.profile!!.personRecord2048,
                "recordFlappyCats" to MyProfilePresenter.profile!!.personRecordCats,
                "recordPianoTiles" to MyProfilePresenter.profile!!.personRecordPiano,
                "recordTetris" to MyProfilePresenter.profile!!.personRecordTetris,
                "games" to MyProfilePresenter.profile!!.games,
                "friends" to MyProfilePresenter.profile!!.friends,
                "likeTo" to MyProfilePresenter.profile!!.likeTo
            )

            try {
                val db = FirebaseFirestore.getInstance()
                db.collection("users").document(MyProfilePresenter.profile!!.personUid)
                    .set(docData).addOnSuccessListener {
                    }
                    .addOnFailureListener {
                        throw ApiException("")
                    }
            } catch (e: Exception) {
            }
        }
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