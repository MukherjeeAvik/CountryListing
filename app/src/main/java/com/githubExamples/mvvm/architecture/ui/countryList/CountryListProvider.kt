package com.githubExamples.mvvm.architecture.ui.countryList

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CountryListProvider {
    @ContributesAndroidInjector(modules = [CountryListModule::class])
    abstract fun provideCountryListFragment(): CountryListFragment

}
