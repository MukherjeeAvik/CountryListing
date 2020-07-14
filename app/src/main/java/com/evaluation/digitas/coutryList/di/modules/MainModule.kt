package com.evaluation.digitas.coutryList.di.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.evaluation.digitas.coutryList.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideFragmentManager(activity: MainActivity): FragmentManager =
        (activity as AppCompatActivity).supportFragmentManager
}
