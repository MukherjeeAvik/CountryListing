package com.evaluation.digitas.coutryList.ui

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evaluation.digitas.coutryList.R
import com.evaluation.digitas.coutryList.navigation.MainNavigator
import com.evaluation.digitas.coutryList.navigation.MainRoutes
import com.evaluation.digitas.coutryList.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.evaluation.digitas.coutryList.ui.base.ViewModelProviderFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var mainNavigator: MainNavigator

    private lateinit var mainViewModel: MainViewModel

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        observeRoutes()
        mainViewModel.navigateToLandingPage(mainNavigator)
    }

    private fun observeRoutes() {
        mainViewModel.observeRoutes().observe(this, Observer { routes ->
            when (routes) {
                is MainRoutes.Routes.gotoListingPage -> {
                    mainNavigator.openListingPage(R.id.fragmentContainer, false)
                }
                is MainRoutes.Routes.gotToDetailsPage -> {
                    mainNavigator.openDetailsPage(R.id.fragmentContainer, true, routes.countryItem)
                }
                is MainRoutes.Routes.goBack -> {
                    mainNavigator.goBack()
                }
            }

        })
    }

    override fun getLifeCycleObserver(): LifecycleObserver {
        return mainViewModel
    }

    override fun getParentLayForSnackBar() = rootLay

}
