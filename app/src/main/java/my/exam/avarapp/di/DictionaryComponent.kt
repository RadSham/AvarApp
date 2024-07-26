package my.exam.avarapp.di

import android.content.Context
import my.exam.avarapp.DictionaryActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier
import javax.inject.Scope

@DictionaryScope
@Component(dependencies = [ApplicationComponent::class], modules = [DictionaryViewModelModule::class])
interface DictionaryActivityComponent {

    fun inject(dictionaryActivity: DictionaryActivity)

    @DictionaryActivityContext
    fun context(): Context

    @Component.Factory
    interface Factory {
        fun create(
            applicationComponent: ApplicationComponent,
            @BindsInstance @DictionaryActivityContext context: Context
        ): DictionaryActivityComponent
    }
}
@Scope
annotation class DictionaryScope

@Qualifier
annotation class DictionaryActivityContext