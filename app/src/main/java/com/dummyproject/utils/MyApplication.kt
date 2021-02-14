package com.dummyproject.utils

import android.app.Application
import com.dummyproject.BuildConfig
import com.facebook.stetho.Stetho
import dagger.Component
import dagger.internal.DaggerCollections

@Component
interface ApplicationComponent {}

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.MY_DEBUG_BUILD)
         Stetho.initializeWithDefaults(this)

    }
}