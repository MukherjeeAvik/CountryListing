package com.githubExamples.mvvm.architecture.navigation

import com.githubExamples.mvvm.architecture.domain.entity.CountryItem

interface MainRoutes {
    sealed class Routes {
        object gotoListingPage : Routes()
        data class gotToDetailsPage(val countryItem: CountryItem) : Routes()
        object goBack : Routes()
    }
}
