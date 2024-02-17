package my.exam.avarapp.di

import android.content.Context
import my.exam.avarapp.App
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ApplicationComponent {
    fun inject(app: App)

    fun context(): Context

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}