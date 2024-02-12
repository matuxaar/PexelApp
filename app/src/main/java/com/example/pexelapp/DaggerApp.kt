package com.example.pexelapp

import android.app.Application
import com.example.pexelapp.di.AppComponent
import com.example.pexelapp.di.DaggerAppComponent


class DaggerApp  : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}