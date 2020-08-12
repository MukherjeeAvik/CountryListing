package com.githubExamples.mvvm.architecture.data.repos.remote

import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse
import com.githubExamples.mvvm.architecture.domain.Repository
import com.githubExamples.mvvm.architecture.utils.API_PATH
import com.githubExamples.mvvm.architecture.utils.HEADER_HOST
import com.githubExamples.mvvm.architecture.utils.HEADER_KEY
import com.githubExamples.mvvm.architecture.utils.USE_QUERY_STRING
import io.reactivex.Completable
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

    override fun saveData(data: CountryListResponse): Completable {
        return Completable.create { emitter ->
            emitter.onComplete()
        }
    }
}
