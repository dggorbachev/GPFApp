package com.singlelab.gpf.ui.my_profile

import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import com.singlelab.gpf.model.profile.Badge
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.Profile
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MyProfileView : LoadingView, ErrorView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProfile(profile: Profile)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToAuth()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToAuth2()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun loadImage(imageUid: String?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onLoadedFriends(friends: List<Person>?)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onLoadedBadges(badges: List<Badge>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNewBadge(hasNewBadges: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoadingFriends(isShow: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNewYearView()
}