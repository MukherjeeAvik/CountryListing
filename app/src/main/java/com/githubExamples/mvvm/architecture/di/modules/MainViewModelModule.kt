package com.githubExamples.mvvm.architecture.di.modules

import androidx.lifecycle.ViewModel
import com.githubExamples.mvvm.architecture.ui.MainViewModel
import com.githubExamples.mvvm.architecture.di.scopes.ViewModelKey
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
