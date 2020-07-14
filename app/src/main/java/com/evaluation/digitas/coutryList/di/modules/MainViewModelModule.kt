package com.evaluation.digitas.coutryList.di.modules

import androidx.lifecycle.ViewModel
import com.evaluation.digitas.coutryList.ui.MainViewModel
import com.evaluation.digitas.coutryList.di.scopes.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}
