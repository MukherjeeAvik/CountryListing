package com.githubExamples.mvvm.architecture.domain.usecase

import com.githubExamples.mvvm.architecture.data.mappers.CountryItemMapper
import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse
import com.githubExamples.mvvm.architecture.data.repos.remote.utils.NoInternetAvailableException
import com.githubExamples.mvvm.architecture.data.repos.remote.utils.UseCaseException
import com.githubExamples.mvvm.architecture.domain.GetCountryListingFromApi
import com.githubExamples.mvvm.architecture.domain.GetCountryListingFromLocal
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.ui.entities.DataWrapper
import com.githubExamples.mvvm.architecture.utils.FILE_NOT_FOUND
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCountryListUseCase(
    private val countryApiRepo: GetCountryListingFromApi,
    private val fileRepository: GetCountryListingFromLocal,
    private val gson: Gson
) : UseCase<UseCaseWrapper<DataWrapper>>() {

    override suspend fun requestForData(): UseCaseWrapper<DataWrapper> {
        return try {
            //get data from either network of Local
            val (dataResponse, dataSource) = getDataFromAvailableSource()
            if (dataSource == Source.NETWORK) {
                //sync to file storage if response is from network
                syncDataToLocal(dataResponse)
            }
            //convert network layer model to domain layer model for UI consumption
            val mappedApiResponse = mapRawDataToModelClass(dataResponse)
            val dataWrapper = DataWrapper(mappedApiResponse, dataSource)
            //return data successfully on completion
            UseCaseWrapper.Success(dataWrapper)
        } catch (thrownExceptions: UseCaseException) {
            /*
            handle exception caused by any of the local
            functions and relay the same to UI layer
             */
            UseCaseWrapper.Failed(thrownExceptions.reason)
        } catch (genericExceptions: Exception) {
            // generic exception handling
            UseCaseWrapper.Failed(ReasonToFail.SOMETHING_WENT_WRONG)
        }
    }

    /**
     * this method gets data from network if available or gets data from local source
     * Also throws exception in case of invalid data
     */
    private suspend fun getDataFromAvailableSource(): Pair<CountryListResponse, Source> {
        return try {
            val apiResponseRaw = countryApiRepo.getData()
            if (!apiResponseRaw.isNullOrEmpty()) {
                Pair(countryApiRepo.getData(), Source.NETWORK)
            } else throw UseCaseException(ReasonToFail.INVALID_API_RESPONSE)
        } catch (ex: NoInternetAvailableException) {
            val cachedResponseRaw = fileRepository.getDataFromLocal()
            if (cachedResponseRaw != FILE_NOT_FOUND) {
                Pair(deSerializeCachedResponse(cachedResponseRaw), Source.LOCAL)
            } else throw UseCaseException(ReasonToFail.NO_NETWORK_AVAILABLE)
        }
    }

    /**
     * method responsible for converting list of countries received in API to model classes
     * for UI consumption
     */
    private suspend fun mapRawDataToModelClass(response: CountryListResponse): List<CountryItem> {
        return withContext(Dispatchers.Default) {
            try {
                response.map { eachCountryItemRaw ->
                    CountryItemMapper().mapFrom(eachCountryItemRaw)
                }.apply {
                    ArrayList<CountryItem>().addAll(this)
                }
            } catch (ex: Exception) {
                throw UseCaseException(ReasonToFail.INVALID_API_RESPONSE)
            }
        }

    }

    /**
     * Storing the API response to file storage
     */
    private suspend fun syncDataToLocal(networkResponse: CountryListResponse) {
        withContext(Dispatchers.Default) {
            val content = gson.toJson(networkResponse)
            fileRepository.saveDataToLocal(content)
        }

    }

    private suspend fun deSerializeCachedResponse(cachedRawResponse: String): CountryListResponse {
        return withContext(Dispatchers.Default) {
            gson.fromJson(cachedRawResponse, CountryListResponse::class.java)
        }

    }

}


