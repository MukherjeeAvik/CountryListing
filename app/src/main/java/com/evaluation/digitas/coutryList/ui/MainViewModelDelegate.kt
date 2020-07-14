package com.evaluation.digitas.coutryList.ui

import com.evaluation.digitas.coutryList.domain.CountryItem
import com.evaluation.digitas.coutryList.navigation.MainNavigator
import com.evaluation.digitas.coutryList.navigation.MainRoutes
import com.evaluation.digitas.coutryList.utils.SingleLiveEvent

interface MainViewModelDelegate {

    fun getListOfCountries()
    fun observeViewStates(): SingleLiveEvent<ViewStates.CountryListStates>
    fun disposeOngoingOperationIfAny()
    fun navigateToLandingPage(mainNavigator: MainNavigator)
    fun navigateToDetailsPage(countryItem: CountryItem)
    fun observeRoutes(): SingleLiveEvent<MainRoutes.Routes>
    fun getCurrentViewState()
    fun goBack()

}
