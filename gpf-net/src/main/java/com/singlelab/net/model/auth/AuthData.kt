package com.singlelab.net.model.auth


object AuthData {
    var accessToken: String? = null
    var refreshToken: String? = null
    var uid: String? = null
    var isAnon: Boolean = false
    var cityId: Int? = null
    var cityName: String? = null
    var age: Int? = null

    fun setAuth(accessToken: String?, refreshToken: String?) {
        AuthData.accessToken = accessToken
        AuthData.refreshToken = refreshToken
    }

    fun setAuthData(
        accessToken: String?,
        refreshToken: String?,
        uid: String?,
        isAnon: Boolean,
        cityId: Int?,
        cityName: String?,
        age: Int?
    ) {
        AuthData.accessToken = accessToken
        AuthData.refreshToken = refreshToken
        AuthData.uid = uid
//        AuthData.isAnon = isAnon
        AuthData.cityId = cityId
        AuthData.cityName = cityName
        AuthData.age = age
    }
}