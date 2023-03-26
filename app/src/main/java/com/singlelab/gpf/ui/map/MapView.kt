package com.singlelab.gpf.ui.map

import com.google.android.gms.maps.model.LatLng
import com.singlelab.gpf.base.view.ErrorView
import com.singlelab.gpf.base.view.LoadingView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MapView : LoadingView, ErrorView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun searchPlace(queryStr: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun searchPlace(latLng: LatLng)
}