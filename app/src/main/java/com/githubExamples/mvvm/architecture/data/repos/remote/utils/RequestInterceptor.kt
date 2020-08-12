package com.githubExamples.mvvm.architecture.data.repos.remote.utils

import android.app.Application
import com.githubExamples.mvvm.architecture.MyApplication
import okhttp3.Interceptor
import okhttp3.Response


class RequestInterceptor(private val app: Application) : Interceptor {
    @Throws(NoInternetAvailableException::class, IllegalArgumentException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!(app as MyApplication).isInternetAvailable())
            throw NoInternetAvailableException()

        return chain.proceed(chain.request())
    }


}
