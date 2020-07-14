package com.evaluation.digitas.coutryList.repos.remote

import com.evaluation.digitas.coutryList.repos.remote.models.CountryListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("{zone}")
    fun getListOfCountriesAsPerZone(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") key: String,
        @Header("useQueryString") useQuery: String,
        @Path("zone") zone: String
    ): Observable<CountryListResponse>


}
