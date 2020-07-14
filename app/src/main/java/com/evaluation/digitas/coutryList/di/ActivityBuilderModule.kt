package com.evaluation.digitas.coutryList.di

import com.evaluation.digitas.coutryList.di.modules.MainModule
import com.evaluation.digitas.coutryList.di.modules.MainViewModelModule
import com.evaluation.digitas.coutryList.di.scopes.MainScope
import com.evaluation.digitas.coutryList.ui.MainActivity
import com.evaluation.digitas.coutryList.ui.countryDetail.CountryDetailModule
import com.evaluation.digitas.coutryList.ui.countryDetail.CountryDetailProvider
import com.evaluation.digitas.coutryList.ui.countryList.CountryListModule
import com.evaluation.digitas.coutryList.ui.countryList.CountryListProvider
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
