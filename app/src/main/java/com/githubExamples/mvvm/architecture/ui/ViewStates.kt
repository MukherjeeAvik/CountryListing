package com.githubExamples.mvvm.architecture.ui

import com.githubExamples.mvvm.architecture.domain.entity.CountryItem

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
