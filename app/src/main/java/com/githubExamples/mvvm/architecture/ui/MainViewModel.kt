package com.githubExamples.mvvm.architecture.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.githubExamples.mvvm.architecture.domain.usecase.GetCountryListUseCase
import com.githubExamples.mvvm.architecture.domain.usecase.Source
import com.githubExamples.mvvm.architecture.domain.usecase.UseCaseWrapper
import com.githubExamples.mvvm.architecture.navigation.Routes
import com.githubExamples.mvvm.architecture.ui.base.BaseViewModel
import com.githubExamples.mvvm.architecture.utils.NO_NETWORK
import com.githubExamples.mvvm.architecture.utils.SOMETHING_WENT_WRONG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val countryListUseCase: GetCountryListUseCase
) : BaseViewModel(), LifecycleObserver, MainViewModelDelegate {


    private val countryListViewStates =
            MutableStateFlow<CountryListStates>(CountryListStates.ShowLoading)

    private val mainNavigationStates =
            MutableStateFlow<Routes>(Routes.GotoListingPage)


    override fun getListOfCountries() {
        viewModelScope.launch {

            when (val useCaseData = countryListUseCase.requestForData()) {

                is UseCaseWrapper.Success -> {
                    //retrieve data received for success
                    val listOfCountries = useCaseData.data.list
                    val currentViewState = CountryListStates.ShowContent(
                            isLoading = false,
                            hasError = false,
                            errorMessage = "",
                            showList = true,
                            countryList = listOfCountries
                    )
                    //setting this view state as the current state of the stateFlow
                    countryListViewStates.value = currentViewState
                }
                is UseCaseWrapper.Failed -> {
                    //creating  a state for failed events
                    val currentViewState = CountryListStates.ShowContent(
                            isLoading = false,
                            hasError = true,
                            errorMessage = useCaseData.reason.name,
                            showList = false,
                            countryList = ArrayList()
                    )
                    //setting this view state as the current state of the stateFlow
                    countryListViewStates.value = currentViewState
                }
            }
        }
    }

    override fun observeViewStates(): StateFlow<CountryListStates> = countryListViewStates
    override fun observeNavigationStates(): StateFlow<Routes> = mainNavigationStates
    override fun registerNavigationRoutes(route: Routes) {
        mainNavigationStates.value = route
    }

    override fun disposeOngoingOperationIfAny() {
    }


}
