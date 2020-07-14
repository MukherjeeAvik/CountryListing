package com.evaluation.digitas.coutryList.di.modules

import android.app.Application
import android.content.Context
import com.evaluation.digitas.coutryList.BuildConfig
import com.evaluation.digitas.coutryList.di.qualifiers.CacheDirectoryPathInfo
import com.evaluation.digitas.coutryList.di.qualifiers.FilterZone
import com.evaluation.digitas.coutryList.utils.*
import com.evaluation.digitas.coutryList.utils.rx.AppSchedulerProvider
import com.evaluation.digitas.coutryList.utils.rx.SchedulerProvider
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



