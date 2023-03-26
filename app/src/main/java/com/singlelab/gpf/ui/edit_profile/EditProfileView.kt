package com.singlelab.gpf.ui.edit_profile

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.profile.NewProfile
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EditProfileView : LoadingView, ErrorView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProfile(profile: NewProfile)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onCompleteUpdate()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLogin(login: String?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showName(name: String?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showAge(age: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDescription(description: String?)
}