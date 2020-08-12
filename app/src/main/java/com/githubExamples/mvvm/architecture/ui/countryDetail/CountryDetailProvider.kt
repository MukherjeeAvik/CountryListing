package com.githubExamples.mvvm.architecture.ui.countryDetail

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CountryDetailProvider {

    @ContributesAndroidInjector(modules = [CountryDetailModule::class])
    abstract fun provideCountryDetailsFragment(): CountryDetailFragment


}
