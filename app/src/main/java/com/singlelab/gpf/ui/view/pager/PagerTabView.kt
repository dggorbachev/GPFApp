package com.singlelab.gpf.ui.view.pager

import android.view.View

interface PagerTabView {
    fun getTitle(): String

    fun getView(): View
}