package com.example.avarapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.avarapp.viewmodel.DictionaryViewModel
import com.example.avarapp.viewmodel.DictionaryViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryViewModel::class)
    abstract fun bindMainViewModel(dictionaryViewModel: DictionaryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DictionaryViewModelFactory): ViewModelProvider.Factory

}