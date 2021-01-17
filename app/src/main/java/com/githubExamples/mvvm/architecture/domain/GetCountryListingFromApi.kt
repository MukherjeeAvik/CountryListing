package com.githubExamples.mvvm.architecture.domain

import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse

interface GetCountryListingFromApi {
    suspend fun getData():CountryListResponse
}