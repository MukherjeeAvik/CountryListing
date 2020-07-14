package com.evaluation.digitas.coutryList.repos.remote

import com.evaluation.digitas.coutryList.repos.Repository
import com.evaluation.digitas.coutryList.repos.remote.models.CountryListResponse
import com.evaluation.digitas.coutryList.utils.API_PATH
import com.evaluation.digitas.coutryList.utils.HEADER_HOST
import com.evaluation.digitas.coutryList.utils.HEADER_KEY
import com.evaluation.digitas.coutryList.utils.USE_QUERY_STRING
import io.reactivex.Observable
import javax.inject.Inject

class GetCountryListFromApi @Inject constructor(
    private val apiService: ApiService
) : Repository<CountryListResponse> {

    override fun getData(vararg params: Any): Observable<CountryListResponse> {
        return apiService.getListOfCountriesAsPerZone(
            host = HEADER_HOST,
            key = HEADER_KEY,
            useQuery = USE_QUERY_STRING,
            zone = API_PATH
        )
    }
}
