package com.githubExamples.mvvm.architecture.ui

import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.navigation.MainNavigator
import com.githubExamples.mvvm.architecture.navigation.MainRoutes
import com.githubExamples.mvvm.architecture.utils.SingleLiveEvent

interface MainViewModelDelegate {

    fun getListOfCountries()
    fun observeViewStates(): SingleLiveEvent<ViewStates.CountryListStates>
    fun disposeOngoingOperationIfAny()
    fun navigateToLandingPage()
    fun navigateToDetailsPage(countryItem: CountryItem)
    fun observeRoutes(): SingleLiveEvent<MainRoutes.Routes>
    fun goBack()

}
