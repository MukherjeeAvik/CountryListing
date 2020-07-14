package com.evaluation.digitas.coutryList.di.modules

import android.app.Application
import com.evaluation.digitas.coutryList.BuildConfig
import com.evaluation.digitas.coutryList.di.qualifiers.BaseUrl
import com.evaluation.digitas.coutryList.repos.remote.ApiService
import com.evaluation.digitas.coutryList.utils.*

import dagger.Module
import dagger.Provides
import com.evaluation.digitas.coutryList.repos.remote.Utils.RequestInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val READ_TIMEOUT_SECONDS = 60
    private val WRITE_TIMEOUT_SECONDS = 60
    private val CONNECT_TIMEOUT_SECONDS = 10
    private val CACHE_50MB = 50 * 1024 * 1024L

    @Provides
    @Singleton
    fun provideOkHttp(
        dataCache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .cache(dataCache)
            .connectTimeout(CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(requestInterceptor)

    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient.Builder, @BaseUrl baseUrl: String): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build()).build()
    }

    @Provides
    @Singleton
    fun provideDataCache(context: Application): Cache = Cache(context.cacheDir, CACHE_50MB)


    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
    }


    @Provides
    @Singleton
    fun provideCustomInterceptor(app: Application) = RequestInterceptor(app)


    @Provides
    @Singleton
    fun provideItemService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @BaseUrl
    fun getBaseUrlForRetrofit(): String {
        return when (BuildConfig.FLAVOR) {
            BUILD_VARIANT_DEV -> {
                DEV_VARIANT_BASE_URL
            }
            BUILD_VARIANT_QA -> {
                QA_VARIANT_BASE_URL
            }
            else -> {
                PROD_VARIANT_BASE_URL
            }
        }

    }
}
