package com.smenedi.modernandroid

import android.app.Application
import timber.log.Timber

class GitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}
