package my.exam.avarapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
/*import my.exam.avarapp.di.ApplicationComponent
import my.exam.avarapp.di.DaggerApplicationComponent*/

@HiltAndroidApp
class App : Application() {

//    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
//        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}