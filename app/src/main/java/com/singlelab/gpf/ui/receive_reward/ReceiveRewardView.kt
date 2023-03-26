package com.singlelab.gpf.ui.receive_reward

import android.graphics.Bitmap
import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ReceiveRewardView : LoadingView, ErrorView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccess()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showImages(images: List<Bitmap>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmptyCardNum()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInvalidCardNum()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmptyImages()
}