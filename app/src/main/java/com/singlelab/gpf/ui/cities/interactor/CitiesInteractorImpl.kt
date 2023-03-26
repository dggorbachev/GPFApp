package com.singlelab.gpf.ui.cities.interactor

import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.model.city.City
import com.singlelab.net.repositories.BaseRepository
import com.singlelab.net.repositories.cities.CitiesRepository

class CitiesInteractorImpl(private val repository: CitiesRepository) : CitiesInteractor,
    BaseInteractor(repository as BaseRepository) {
    override suspend fun getCities(): List<City>? {
        return repository.getCities()?.mapNotNull {
            City.fromResponse(it)
        }
    }
}