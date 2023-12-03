package com.example.avarapp

import android.app.Application
import com.example.avarapp.di.ApplicationComponent
import com.example.avarapp.di.DaggerApplicationComponent

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}