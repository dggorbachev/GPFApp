package com.singlelab.gpf.interactive_games.neon_tetris.common

import android.app.Activity
import android.view.inputmethod.InputMethodManager


fun hideSoftKeyboard(activity: Activity) {

    val inputMethodManager: InputMethodManager = activity.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    if (activity.currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}