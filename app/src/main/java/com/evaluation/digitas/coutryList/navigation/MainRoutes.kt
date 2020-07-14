package com.evaluation.digitas.coutryList.navigation

import com.evaluation.digitas.coutryList.domain.CountryItem

interface MainRoutes {
    sealed class Routes {
        object gotoListingPage : Routes()
        data class gotToDetailsPage(val countryItem: CountryItem) : Routes()
        object goBack : Routes()
    }
}
