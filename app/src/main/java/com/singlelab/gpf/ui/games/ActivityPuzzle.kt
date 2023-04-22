package com.singlelab.gpf.ui.games

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.WindowManager
import android.webkit.WebSettings.RenderPriority
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.singlelab.gpf.R
import java.util.*


class ActivityPuzzle : AppCompatActivity() {

    private var mWebView: WebView? = null

    private val mLastBackPress: Long = 0
    private val mBackPressThreshold: Long = 3500
    private var mLastTouch: Long = 0
    private val mTouchThreshold: Long = 2000
    private var pressBackToast: Toast? = null
    private val IS_FULLSCREEN_PREF = "is_fullscreen_pref"

    @SuppressLint("ClickableViewAccessibility", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2048)

        // Load webview with game
        mWebView = findViewById(R.id.mainWebView)
        val settings = mWebView!!.getSettings()
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowContentAccess = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        mWebView!!.setWebViewClient(WebViewClient())
        settings.databaseEnabled = true
        settings.setRenderPriority(RenderPriority.HIGH)
        settings.databasePath = filesDir.parentFile.path + "/databases"


            mWebView!!.loadUrl("file:///android_asset/2048/index.html?lang=" + Locale.getDefault().language)

        // If there is a previous instance restore it in the webview
//        if (savedInstanceState != null) {
//            mWebView!!.restoreState(savedInstanceState)
//        } else {
//            mWebView!!.loadUrl("file:///android_asset/2048/index.html?lang=" + Locale.getDefault().language)
//        }

//        mWebView!!.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent ->
//            val currentTime = System.currentTimeMillis()
//            if (event.action == MotionEvent.ACTION_UP && Math.abs(currentTime - mLastTouch) > mTouchThreshold
//            ) {
//                val toggledFullScreen: Boolean = !isFullScreen()
//                saveFullScreen(toggledFullScreen)
//                applyFullScreen(toggledFullScreen)
//            } else if (event.action == MotionEvent.ACTION_DOWN) {
//                mLastTouch = currentTime
//            }
//            false
//        })

//        pressBackToast = Toast.makeText(
//            applicationContext, "Нажмите назад, чтобы выйти",
//            Toast.LENGTH_SHORT
//        )

    }

    override fun onBackPressed() {
        super.onBackPressed()
//        mWebView!!.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mWebView!!.saveState(outState)
    }

    /**
     * Saves the full screen setting in the SharedPreferences
     *
     * @param isFullScreen boolean value
     */
    private fun saveFullScreen(isFullScreen: Boolean) {
        // save in preferences
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean(IS_FULLSCREEN_PREF, isFullScreen)
        editor.apply()
    }

    private fun isFullScreen(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
            IS_FULLSCREEN_PREF,
            true
        )
    }

    /**
     * Toggles the activity's fullscreen mode by setting the corresponding window flag
     *
     * @param isFullScreen boolean value
     */
    private fun applyFullScreen(isFullScreen: Boolean) {
        if (isFullScreen) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}