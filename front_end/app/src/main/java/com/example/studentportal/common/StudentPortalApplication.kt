package com.example.studentportal.common

import android.app.Application
import com.example.studentportal.common.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StudentPortalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StudentPortalApplication)
            modules(appModule)
        }
    }
}
