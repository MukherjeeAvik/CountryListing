package com.evaluation.digitas.coutryList.usecase

import com.evaluation.digitas.coutryList.di.qualifiers.FilterZone
import com.evaluation.digitas.coutryList.domain.CountryItem
import com.evaluation.digitas.coutryList.domain.CountryItemMapper
import com.evaluation.digitas.coutryList.repos.local.FileRepository
import com.evaluation.digitas.coutryList.repos.remote.GetCountryListFromApi
import com.evaluation.digitas.coutryList.repos.remote.models.CountryListResponse
import com.evaluation.digitas.coutryList.repos.remote.models.CountryListResponseItem
import com.evaluation.digitas.coutryList.utils.COUNTRY_LIST_FILE_NAME
import com.evaluation.digitas.coutryList.utils.FILE_NOT_FOUND
import com.evaluation.digitas.coutryList.utils.rx.SchedulerProvider
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(
    private val countryApiRepo: GetCountryListFromApi,
    private val fileRepository: FileRepository,
    private val schedulerProvider: SchedulerProvider,
    private val gson: Gson,
    @FilterZone private val filter: String
) : UseCase<UseCaseWrapper<DataWrapper>>() {

    init {
        fileRepository.fileName = COUNTRY_LIST_FILE_NAME
    }

    override fun subscribeForData(vararg params: Any): Observable<UseCaseWrapper<DataWrapper>> {
        compositeDisposable.add(
            countryApiRepo.getData()
                .subscribeOn(schedulerProvider.computation())
                .flatMap { return@flatMap syncDataToLocal(it) }
                .compose(transformCountryObjects())
                .map { return@map mapToDataWrapper(Source.NETWORK, it) }
                .onErrorResumeNext(getDataFromLocal())
                .subscribe({ wrappedData ->
                    dataRepo.onNext(UseCaseWrapper.Success(wrappedData))
                }, { exception ->
                    dataRepo.onNext(UseCaseWrapper.Failed(ReasonToFail.SOMETHING_WENT_WRONG))

                })

        )
        return dataRepo
    }

    private fun getDataFromLocal(): Observable<DataWrapper> {
        return fileRepository.getDataFromLocal().map {
            if (it == FILE_NOT_FOUND) {
                return@map ArrayList<CountryListResponseItem>()
            } else {
                return@map gson.fromJson(it, CountryListResponse::class.java)
            }
        }.compose(transformCountryObjects())
            .map { mapToDataWrapper(Source.LOCAL, it) }
            .onErrorReturn {
                return@onErrorReturn mapToDataWrapper(Source.LOCAL, ArrayList())
            }
    }


    private fun mapToDataWrapper(source: Source, listOfCountries: List<CountryItem>) =
        DataWrapper(listOfCountries, source)

    private fun syncDataToLocal(response: CountryListResponse): Observable<CountryListResponse> {
        return Observable.create { emitter ->
            try {
                val content = gson.toJson(response)
                fileRepository.saveData(content)
                emitter.onNext(response)
                emitter.onComplete()
            } catch (ex: Exception) {
                emitter.onNext(response)
                emitter.onComplete()
            }

        }


    }

    private fun transformCountryObjects(): ObservableTransformer<List<CountryListResponseItem>, List<CountryItem>> {
        return ObservableTransformer { allCountries ->
            allCountries.flatMapIterable { return@flatMapIterable it }
                .filter { return@filter it.region == filter }
                .map { eachCountryResponseItem ->
                    return@map CountryItemMapper().mapFrom(eachCountryResponseItem)
                }.toList().toObservable()

        }

    }

}

data class DataWrapper(val list: List<CountryItem>, val source: Source)
