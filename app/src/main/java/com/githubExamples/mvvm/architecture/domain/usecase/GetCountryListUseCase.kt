package com.githubExamples.mvvm.architecture.domain.usecase

import com.githubExamples.mvvm.architecture.data.mappers.CountryItemMapper
import com.githubExamples.mvvm.architecture.data.repos.local.FileRepository
import com.githubExamples.mvvm.architecture.data.repos.remote.GetCountryListFromApi
import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse
import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponseItem
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.ui.entities.DataWrapper
import com.githubExamples.mvvm.architecture.utils.COUNTRY_LIST_FILE_NAME
import com.githubExamples.mvvm.architecture.utils.FILE_NOT_FOUND
import com.githubExamples.mvvm.architecture.utils.rx.SchedulerProvider
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class GetCountryListUseCase @Inject constructor(
    private val countryApiRepo: GetCountryListFromApi,
    private val fileRepository: FileRepository,
    private val schedulerProvider: SchedulerProvider,
    private val gson: Gson
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
                    // There is no internet and cached data is unavailable
                    if (wrappedData.source == Source.LOCAL && wrappedData.list.isEmpty()) {
                        dataRepo.onNext(UseCaseWrapper.Failed(ReasonToFail.NO_NETWORK_AVAILABLE))
                    } else {
                        // Data is available, either from cached and network
                        dataRepo.onNext(UseCaseWrapper.Success(wrappedData))
                    }
                }, {
                    // Handle generic exceptions
                    dataRepo.onNext(UseCaseWrapper.Failed(ReasonToFail.SOMETHING_WENT_WRONG))
                })
        )
        return dataRepo
    }

    private fun getDataFromLocal(): Observable<DataWrapper> {
        return fileRepository.getData().map {
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
                fileRepository.saveData(content).subscribe()
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
                .map { eachCountryResponseItem ->
                    return@map CountryItemMapper()
                        .mapFrom(eachCountryResponseItem)
                }.toList().toObservable()
        }
    }

}


