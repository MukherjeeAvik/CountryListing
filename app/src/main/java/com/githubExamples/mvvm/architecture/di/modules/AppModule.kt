package com.githubExamples.mvvm.architecture.di.modules

import android.app.Application
import android.content.Context
import com.githubExamples.mvvm.acrhitecture.BuildConfig
import com.githubExamples.mvvm.architecture.data.repos.local.FileRepository
import com.githubExamples.mvvm.architecture.data.repos.remote.GetCountryListingApiImpl
import com.githubExamples.mvvm.architecture.di.qualifiers.CacheDirectoryPathInfo
import com.githubExamples.mvvm.architecture.di.qualifiers.FilterZone
import com.githubExamples.mvvm.architecture.domain.usecase.GetCountryListUseCase
import com.githubExamples.mvvm.architecture.utils.*
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
    @CacheDirectoryPathInfo
    fun provideCacheDirectory(context: Context): File {
        return context.cacheDir
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()


    @Provides
    fun provideLocalRepository(@CacheDirectoryPathInfo cacheDirectoryFile: File): FileRepository {
        return FileRepository("COUNTRY_LISTING_LOCAL", cacheDirectoryFile)
    }


    @Provides
    fun provideCountryListingUseCase(countryListApi: GetCountryListingApiImpl, fileRepository: FileRepository, gson: Gson): GetCountryListUseCase {
        return GetCountryListUseCase(countryListApi, fileRepository, gson)
    }


}



