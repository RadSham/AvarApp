package my.exam.avarapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.exam.avarapp.viewmodel.DictionaryViewModel
import my.exam.avarapp.viewmodel.DictionaryViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DictionaryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryViewModel::class)
    abstract fun bindMainViewModel(dictionaryViewModel: DictionaryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DictionaryViewModelFactory): ViewModelProvider.Factory

}