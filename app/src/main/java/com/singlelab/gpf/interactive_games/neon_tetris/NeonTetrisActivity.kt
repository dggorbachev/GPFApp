package com.singlelab.gpf.interactive_games.neon_tetris

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.singlelab.gpf.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_neon_tetris.launchGame
import kotlinx.android.synthetic.main.activity_neon_tetris.profile_highscore
import kotlinx.android.synthetic.main.activity_neon_tetris.profile_name

open class NeonTetrisActivity : AppCompatActivity(), View.OnClickListener{

    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var userName = ""
    private var highScore = "0"

    private var you: Boolean = false
    private var em: Boolean = false
    private var grid: Boolean = false
    private var swipe: Boolean = false
    private var fullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neon_tetris)

        launchGame.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser

        if (userName.isNotEmpty()) {
            profile_name.visibility = View.VISIBLE
            profile_name.text = resources.getString(R.string.welcome1, userName)
        } else {
            profile_name.visibility = View.INVISIBLE
            profile_highscore.visibility = View.INVISIBLE
        }
    }

    override fun onClick(v: View?) {

        when (v) {
            launchGame -> {
                val intent: Intent = Intent(this, GameActivity::class.java)
                intent.putExtra("highscore", highScore)
                intent.putExtra("addYouBlock", you)
                intent.putExtra("addEmBlock", em)
                intent.putExtra("addgrid", grid)
                intent.putExtra("addswipe", swipe)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK){
                you = data!!.getBooleanExtra("addYouBlock", false)
                em = data.getBooleanExtra("addEmBlock", false)
                grid = data.getBooleanExtra("addgrid", false)
                swipe = data.getBooleanExtra("addswipe", false)
                fullscreen = data.getBooleanExtra("addFullscreen", false)
            }
        } else if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Log In Successful!", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == 2){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "SignUp Successful!", Toast.LENGTH_LONG).show()
            }
        }
    }
}


