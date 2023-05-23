package com.singlelab.gpf.ui.filters.event

import android.util.Log
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.model.event.Distance
import com.singlelab.gpf.model.event.EventType
import com.singlelab.gpf.model.event.FilterEvent
import com.singlelab.gpf.model.profile.FilterPerson
import com.singlelab.gpf.new_features.games_model.GamePerson
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.swiper_event.SwiperEventPresenter
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class FilterPresenter @Inject constructor(preferences: Preferences?) : MvpPresenter<FilterView>() {

    var filterEvent: FilterEvent? = null
    var filterPerson: FilterPerson? = null

    fun isEvent() = filterEvent != null

    override fun attachView(view: FilterView?) {
        super.attachView(view)
        filterEvent?.let {
            viewState.showTypes(it.selectedTypes)
        }
    }

    fun changeDistance(distance: Int) {
        filterEvent?.let {
            it.distance = Distance.find(distance)
            viewState.showDistance(it.distance)
        }
    }

    fun setCity(city: City?) {
        filterEvent?.let {
            it.cityId = city?.cityId
            it.cityName = city?.cityName
            viewState.showCity(city?.cityName)
        }
        filterPerson?.let {
            it.cityId = city?.cityId
            it.cityName = city?.cityName
            viewState.showCity(city?.cityName)
        }
    }

    fun setUserLocation(longitude: Double, latitude: Double) {
        filterEvent?.let {
            it.longitude = longitude
            it.latitude = latitude
        }
    }

    fun setDate(firstDate: Long?, secondDate: Long?) {
        filterEvent?.minimalStartTime = firstDate
        filterEvent?.maximalEndTime = secondDate
        viewState.showDate(firstDate, secondDate)
    }

    fun onClickType(type: Int) {
        filterEvent ?: return
        if (filterEvent!!.selectedTypes.find { it.id == type } != null) {
            removeType(type)
        } else {
            addType(type)
        }
    }

    private fun addType(type: Int) {
        filterEvent?.selectedTypes?.apply {
            add(EventType.findById(type))
            viewState.showTypes(this)
        }
        SwiperEventPresenter.gameChosen.add(
            when (type) {
                0 -> GamePerson.DOTA
                1 -> GamePerson.CSGO
                2 -> GamePerson.OVERWATCH
                3 -> GamePerson.VALORANT
                4 -> GamePerson.PUBG
                5 -> GamePerson.DIABLO
                else -> GamePerson.DOTA
            }
        )
    }

    private fun removeType(type: Int) {
        filterEvent?.selectedTypes?.apply {
            removeAll { it.id == type }
            viewState.showTypes(this)
        }
        SwiperEventPresenter.gameChosen.remove(
            when (type) {
                0 -> GamePerson.DOTA
                1 -> GamePerson.CSGO
                2 -> GamePerson.OVERWATCH
                3 -> GamePerson.VALORANT
                4 -> GamePerson.PUBG
                5 -> GamePerson.DIABLO
                else -> GamePerson.DOTA
            }
        )
    }
}