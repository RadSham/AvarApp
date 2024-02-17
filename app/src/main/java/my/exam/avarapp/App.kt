package my.exam.avarapp

import android.app.Application
import my.exam.avarapp.di.ApplicationComponent
import my.exam.avarapp.di.DaggerApplicationComponent

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}