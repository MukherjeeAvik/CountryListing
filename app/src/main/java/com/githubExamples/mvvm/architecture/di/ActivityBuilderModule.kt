package com.githubExamples.mvvm.architecture.di

import com.githubExamples.mvvm.architecture.di.modules.MainModule
import com.githubExamples.mvvm.architecture.di.modules.MainViewModelModule
import com.githubExamples.mvvm.architecture.di.scopes.MainScope
import com.githubExamples.mvvm.architecture.ui.MainActivity
import com.githubExamples.mvvm.architecture.ui.countryDetail.CountryDetailModule
import com.githubExamples.mvvm.architecture.ui.countryDetail.CountryDetailProvider
import com.githubExamples.mvvm.architecture.ui.countryList.CountryListModule
import com.githubExamples.mvvm.architecture.ui.countryList.CountryListProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {
    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class,
            MainViewModelModule::class,
            CountryListModule::class,
            CountryListProvider::class,
            CountryDetailModule::class,
            CountryDetailProvider::class]
    )
    abstract fun bindMainActivity(): MainActivity

}
