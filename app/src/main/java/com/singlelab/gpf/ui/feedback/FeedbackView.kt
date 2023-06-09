package com.singlelab.gpf.ui.feedback

import android.graphics.Bitmap
import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface FeedbackView : LoadingView, ErrorView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessSendFeedback()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmptyFeedback()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showImages(images: List<Bitmap>)
}