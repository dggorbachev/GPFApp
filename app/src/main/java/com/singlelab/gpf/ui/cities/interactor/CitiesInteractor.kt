package com.singlelab.gpf.ui.cities.interactor

import com.singlelab.gpf.model.city.City

interface CitiesInteractor {
    suspend fun getCities(): List<City>?
}