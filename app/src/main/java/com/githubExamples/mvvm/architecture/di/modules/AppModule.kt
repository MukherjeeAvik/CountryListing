package com.githubExamples.mvvm.architecture.di.modules

import android.app.Application
import android.content.Context
import com.githubExamples.mvvm.acrhitecture.BuildConfig
import com.githubExamples.mvvm.architecture.di.qualifiers.CacheDirectoryPathInfo
import com.githubExamples.mvvm.architecture.di.qualifiers.FilterZone
import com.githubExamples.mvvm.architecture.utils.*
import com.githubExamples.mvvm.architecture.utils.rx.AppSchedulerProvider
import com.githubExamples.mvvm.architecture.utils.rx.SchedulerProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(context: Application): Context = context

    @Provides
    @Singleton
    @FilterZone
    fun getFilterAsPerEnvironment(): String {

        return when (BuildConfig.FLAVOR) {
            BUILD_VARIANT_DEV -> {
                DEV_VARIANT_QUERY
            }
            BUILD_VARIANT_QA -> {
                QA_VARIANT_QUERY
            }
            else -> {
                PROD_VARIANT_QUERY
            }
        }


    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    @CacheDirectoryPathInfo
    fun provideCacheDirectory(context: Context): File {
        return context.cacheDir
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()


}



