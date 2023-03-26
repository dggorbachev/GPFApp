package com.singlelab.gpf.base.view

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LoadingView : BaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoading(isShow: Boolean, withoutBackground: Boolean = false)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoadingText(text: String)
}