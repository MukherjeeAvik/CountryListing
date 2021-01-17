package com.githubExamples.mvvm.architecture.ui

import com.githubExamples.mvvm.architecture.navigation.Routes
import kotlinx.coroutines.flow.StateFlow

interface MainViewModelDelegate {

    fun getListOfCountries()
    fun observeViewStates(): StateFlow<CountryListStates>
    fun observeNavigationStates(): StateFlow<Routes>
    fun registerNavigationRoutes(route:Routes)
    fun disposeOngoingOperationIfAny()
}
