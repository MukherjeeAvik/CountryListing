package com.githubExamples.mvvm.architecture.di.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.githubExamples.mvvm.architecture.data.repos.local.FileRepository
import com.githubExamples.mvvm.architecture.data.repos.remote.GetCountryListFromApi
import com.githubExamples.mvvm.architecture.di.qualifiers.FilterZone
import com.githubExamples.mvvm.architecture.domain.usecase.GetCountryListUseCase
import com.githubExamples.mvvm.architecture.logging.*
import com.githubExamples.mvvm.architecture.ui.MainActivity
import com.githubExamples.mvvm.architecture.utils.rx.SchedulerProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideFragmentManager(activity: MainActivity): FragmentManager =
        (activity as AppCompatActivity).supportFragmentManager


    @Provides
    fun provideLogMaster(consoleLogger: ConsoleLogger) = LogMaster(consoleLogger)

    @Provides
    fun provideConsoleLogger(localLogger: LocalLogger) = ConsoleLogger(localLogger)

    @Provides
    fun provideLocalLogger(remoteLogger: RemoteLogger) = LocalLogger(remoteLogger)


    @Provides
    fun provideRemoteLogger() = RemoteLogger()
    

}


