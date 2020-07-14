package com.evaluation.digitas.coutryList

import android.content.Context
import android.net.ConnectivityManager
import com.evaluation.digitas.coutryList.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {



    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val res = activeNetworkInfo != null && activeNetworkInfo.isConnected
        return res
    }


}
