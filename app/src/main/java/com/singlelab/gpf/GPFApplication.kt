package com.singlelab.gpf

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import com.singlelab.gpf.model.Const
import com.singlelab.gpf.pref.Preferences
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import java.lang.Exception


@HiltAndroidApp
class GPFApplication : Application() {
    companion object {
        var preferences: Preferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(getSharedPreferences(Const.PREF, Context.MODE_PRIVATE))
        val processId = Process.myPid()
        val activityManager =
            applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        try {
            for (processInfo in activityManager.runningAppProcesses) {
                if (processInfo.pid == processId && processInfo.processName != null && (processInfo.processName.endsWith(
                        ":acra"
                    ) || processInfo.processName.endsWith(":outbox"))
                ) {
                    break
                }
            }
        } catch (e: Exception) {
        }
    }
}