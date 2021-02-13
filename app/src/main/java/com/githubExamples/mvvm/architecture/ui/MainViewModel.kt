package com.githubExamples.mvvm.architecture.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.githubExamples.mvvm.architecture.domain.usecase.GetCountryListUseCase
import com.githubExamples.mvvm.architecture.domain.usecase.UseCaseWrapper
import com.githubExamples.mvvm.architecture.navigation.Routes
import com.githubExamples.mvvm.architecture.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val countryListUseCase: GetCountryListUseCase
) : BaseViewModel(), LifecycleObserver, MainViewModelDelegate {


    private val countryListViewStates =
            MutableStateFlow<CountryListStates>(CountryListStates.ShowLoading)

    private val mainNavigationStates = MutableStateFlow<Routes>(Routes.GotoListingPage)

    override fun getListOfCountries() {
        viewModelScope.launch {

            when (val useCaseData = countryListUseCase.requestForData()) {

                is UseCaseWrapper.Success -> {

                    val listOfCountries = useCaseData.data.list
                    val viewState = CountryListStates.ShowContent(
                            isLoading = false,
                            hasError = false,
                            errorMessage = "",
                            showList = true,
                            countryList = listOfCountries
                    )

                    countryListViewStates.value = viewState
                }
                is UseCaseWrapper.Failed -> {
                    val viewState = CountryListStates.ShowContent(
                            isLoading = false,
                            hasError = true,
                            errorMessage = useCaseData.reason.name,
                            showList = false,
                            countryList = ArrayList()
                    )

                    countryListViewStates.value = viewState
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
