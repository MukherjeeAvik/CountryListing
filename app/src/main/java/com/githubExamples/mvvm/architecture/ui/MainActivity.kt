package com.githubExamples.mvvm.architecture.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.githubExamples.mvvm.acrhitecture.R
import com.githubExamples.mvvm.acrhitecture.databinding.ActivityMainBinding
import com.githubExamples.mvvm.architecture.navigation.MainNavigator
import com.githubExamples.mvvm.architecture.navigation.Routes
import com.githubExamples.mvvm.architecture.ui.base.BaseActivity
import com.githubExamples.mvvm.architecture.ui.base.ViewModelProviderFactory
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

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
        mainViewModel.registerNavigationRoutes(Routes.GotoListingPage)

    }

    private fun observeRoutes() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.observeNavigationStates().collect { routes ->
                when (routes) {
                    is Routes.GotoListingPage -> {
                        mainNavigator.openListingPage(R.id.fragmentContainer, false)
                    }
                    is Routes.GotoDetailsPage -> {
                        mainNavigator.openDetailsPage(
                                R.id.fragmentContainer,
                                true,
                                routes.countryItem
                        )
                    }
                    is Routes.GoBack -> {
                        mainNavigator.goBack()
                    }
                }

            }
        }
    }

    override fun getLifeCycleObserver(): LifecycleObserver {
        return mainViewModel
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate


}
