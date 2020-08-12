package com.githubExamples.mvvm.architecture.ui

import androidx.lifecycle.LifecycleObserver
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.navigation.MainNavigator
import com.githubExamples.mvvm.architecture.navigation.MainRoutes
import com.githubExamples.mvvm.architecture.ui.base.BaseViewModel
import com.githubExamples.mvvm.architecture.domain.usecase.GetCountryListUseCase
import com.githubExamples.mvvm.architecture.domain.usecase.Source
import com.githubExamples.mvvm.architecture.domain.usecase.UseCaseWrapper
import com.githubExamples.mvvm.architecture.utils.NO_NETWORK
import com.githubExamples.mvvm.architecture.utils.SOMETHING_WENT_WRONG
import com.githubExamples.mvvm.architecture.utils.SingleLiveEvent
import com.githubExamples.mvvm.architecture.utils.rx.SchedulerProvider
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val countryListUseCase: GetCountryListUseCase,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(), LifecycleObserver, MainViewModelDelegate {

    private val countryListViewStates: SingleLiveEvent<ViewStates.CountryListStates>
            by lazy { SingleLiveEvent<ViewStates.CountryListStates>() }
    private val navigationRoutes: SingleLiveEvent<MainRoutes.Routes>
            by lazy { SingleLiveEvent<MainRoutes.Routes>() }

    private var lastObservedState: ViewStates.CountryListStates? = null
    private var lastOpenedDetailsOfCountry: CountryItem? = null


    override fun getListOfCountries() {
        compositeDisposable.add(
            countryListUseCase.subscribeForData()
                .doOnSubscribe { countryListViewStates.postValue(ViewStates.CountryListStates.ShowLoading) }
                .subscribeOn(schedulerProvider.io())
                .subscribe({ countryListDataWrapper ->
                    when (countryListDataWrapper) {
                        is UseCaseWrapper.Success -> {
                            var hasError_ = false
                            var errorMesage_ = ""

                            if (countryListDataWrapper.data.source == Source.NETWORK) {
                                hasError_ = false
                                errorMesage_ = ""
                            } else if (countryListDataWrapper.data.source == Source.LOCAL) {
                                hasError_ = true
                                errorMesage_ = NO_NETWORK
                            }

                            val currentState = ViewStates.CountryListStates.ShowContent(
                                isLoading = false,
                                hasError = hasError_,
                                errorMessage = errorMesage_,
                                showList = true,
                                countryList = countryListDataWrapper.data.list
                            )
                            cacheLastState(currentState)
                            countryListViewStates.postValue(currentState)

                        }

                        is UseCaseWrapper.Failed -> {
                            val currentState = ViewStates.CountryListStates.ShowContent(
                                isLoading = false,
                                hasError = true,
                                errorMessage = NO_NETWORK,
                                showList = false,
                                countryList = ArrayList()
                            )
                            cacheLastState(currentState)
                            countryListViewStates.postValue(currentState)

                        }
                    }


                }, {
                    it.printStackTrace()
                    val currentState = ViewStates.CountryListStates.ShowContent(
                        isLoading = false,
                        hasError = true,
                        errorMessage = SOMETHING_WENT_WRONG,
                        showList = false,
                        countryList = ArrayList()
                    )
                    cacheLastState(currentState)
                    countryListViewStates.postValue(currentState)

                })
        )
    }

    override fun observeViewStates(): SingleLiveEvent<ViewStates.CountryListStates> =
        countryListViewStates

    override fun disposeOngoingOperationIfAny() {
        countryListUseCase.unsubscribeFromDataSource()
        compositeDisposable.clear()
    }

    override fun navigateToLandingPage(mainNavigator: MainNavigator) {


        if (mainNavigator.isDetailsPageShowing())
            navigationRoutes.postValue(lastOpenedDetailsOfCountry?.let {
                MainRoutes.Routes.gotToDetailsPage(
                    it
                )
            })
        else {
            navigationRoutes.postValue(MainRoutes.Routes.gotoListingPage)
        }
    }

    override fun navigateToDetailsPage(countryItem: CountryItem) {
        lastOpenedDetailsOfCountry = countryItem
        navigationRoutes.postValue(MainRoutes.Routes.gotToDetailsPage(countryItem))
    }

    override fun goBack() {
        navigationRoutes.postValue(MainRoutes.Routes.goBack)
    }

    override fun observeRoutes(): SingleLiveEvent<MainRoutes.Routes> = navigationRoutes

    override fun getCurrentViewState() {

        if (lastObservedState == null)
            getListOfCountries()
        else
            countryListViewStates.postValue(lastObservedState)
    }

    private fun cacheLastState(state: ViewStates.CountryListStates) {
        lastObservedState = state
    }


}
