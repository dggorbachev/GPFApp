package com.singlelab.gpf.ui.cities

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.model.city.City
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.cities.interactor.CitiesInteractor
import com.singlelab.net.exceptions.ApiException
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class CitiesPresenter @Inject constructor(
    private val interactor: CitiesInteractor,
    preferences: Preferences?
) : BasePresenter<CitiesView>(preferences, interactor as BaseInteractor) {

    var containsAnyCity: Boolean = false

    private var allCities: List<City>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCities()
    }

    fun filter(queryStr: String) {
        if (queryStr.isBlank()) {
            viewState.showCities(allCities, containsAnyCity)
        } else {
            allCities?.let { allCities ->
                viewState.showCities(allCities.filter {
                    it.cityName.toUpperCase(Const.RUS_LOCALE)
                        .startsWith(queryStr.toUpperCase(Const.RUS_LOCALE))
                })
            }
        }
    }

    private fun loadCities() {
        viewState.showLoading(true)
        invokeSuspend {
            try {
                allCities = interactor.getCities()
                runOnMainThread {
                    viewState.showLoading(false)
                    viewState.showCities(allCities, containsAnyCity)
                }
            } catch (e: ApiException) {
                runOnMainThread {
                    viewState.showLoading(false)
                    viewState.showError(e.message)
                }
            }
        }
    }
}