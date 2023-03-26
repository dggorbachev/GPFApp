package com.singlelab.gpf

import android.app.Application
import android.content.Context
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.pref.Preferences
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GPFApplication : Application() {
    companion object {
        var preferences: Preferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(getSharedPreferences(Const.PREF, Context.MODE_PRIVATE))
        initAppmetrica()
    }

    private fun initAppmetrica() {
        val config = YandexMetricaConfig.newConfigBuilder(getString(R.string.appmetrica_key)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}