package com.evaluation.digitas.coutryList.ui

import com.evaluation.digitas.coutryList.domain.CountryItem

interface ViewStates {
    sealed class CountryListStates {
        data class ShowContent(
            val isLoading: Boolean,
            val hasError: Boolean,
            val errorMessage: String,
            val showList: Boolean,
            val countryList: List<CountryItem>
        ) : CountryListStates()

        object ShowLoading : CountryListStates()
    }
}
