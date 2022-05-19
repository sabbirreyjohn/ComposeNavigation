package com.androidrey.composenavigation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication : Application() {
    companion object {

        private var appInstance: TheApplication? = null

        val instance: TheApplication
            @Synchronized get() {
                if (appInstance == null) {
                    appInstance =
                        TheApplication()
                }
                return appInstance!!
            }
    }

    override fun onCreate() {
        appInstance = this
        super.onCreate()
    }
}