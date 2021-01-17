package com.githubExamples.mvvm.architecture.data.repos.remote

import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse
import com.githubExamples.mvvm.architecture.domain.GetCountryListingFromApi
import com.githubExamples.mvvm.architecture.utils.API_PATH
import com.githubExamples.mvvm.architecture.utils.HEADER_HOST
import com.githubExamples.mvvm.architecture.utils.HEADER_KEY
import com.githubExamples.mvvm.architecture.utils.USE_QUERY_STRING
import javax.inject.Inject
class GetCountryListingApiImpl @Inject constructor(
    private val apiService: ApiService
) : GetCountryListingFromApi {


    override suspend fun getData(): CountryListResponse {
        return apiService.getListOfCountriesAsPerZone(
            host = HEADER_HOST,
            key = HEADER_KEY,
            useQuery = USE_QUERY_STRING,
            zone = API_PATH
        )
    }


}
