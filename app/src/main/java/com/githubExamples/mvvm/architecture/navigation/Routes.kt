package com.githubExamples.mvvm.architecture.navigation

import com.githubExamples.mvvm.architecture.domain.entity.CountryItem

sealed class Routes {
    object GotoListingPage : Routes()
    data class GotoDetailsPage(val countryItem: CountryItem) : Routes()
    object GoBack : Routes()
}

