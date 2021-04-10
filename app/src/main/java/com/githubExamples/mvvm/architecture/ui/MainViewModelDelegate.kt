package com.githubExamples.mvvm.architecture.ui

import androidx.lifecycle.MutableLiveData
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.navigation.MainNavigator
import com.githubExamples.mvvm.architecture.navigation.MainRoutes
import com.githubExamples.mvvm.architecture.utils.SingleLiveEvent

interface MainViewModelDelegate {

    fun getListOfCountries()
    fun observeViewStates(): MutableLiveData<ViewStates.CountryListStates>
    fun disposeOngoingOperationIfAny()
    fun navigateToLandingPage()
    fun navigateToDetailsPage(countryItem: CountryItem)
    fun observeRoutes(): SingleLiveEvent<MainRoutes.Routes>
    fun goBack()

}
