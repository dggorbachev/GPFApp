package com.singlelab.gpf.model.view

import com.google.android.material.snackbar.Snackbar
import com.singlelab.gpf.R

enum class ToastType(
    val colorResId: Int = R.color.colorSnackbar,
    val drawableResId: Int?,
    val length: Int = Snackbar.LENGTH_LONG
) {
    ERROR_RETRY(
        drawableResId = R.drawable.ic_retry,
        length = Snackbar.LENGTH_INDEFINITE
    ),
    ERROR(
        drawableResId = R.drawable.ic_warning
    ),
    WARNING(
        drawableResId = R.drawable.ic_warning
    ),
    SUCCESS(
        colorResId = R.color.colorSuccess,
        drawableResId = R.drawable.ic_success
    )
}