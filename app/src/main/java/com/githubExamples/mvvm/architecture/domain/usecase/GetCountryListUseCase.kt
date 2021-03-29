package com.githubExamples.mvvm.architecture.domain.usecase

import com.githubExamples.mvvm.architecture.data.mappers.CountryItemMapper
import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse
import com.githubExamples.mvvm.architecture.di.qualifiers.FilterZone
import com.githubExamples.mvvm.architecture.domain.GetCountryListingFromApi
import com.githubExamples.mvvm.architecture.domain.GetCountryListingFromLocal
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.ui.entities.DataWrapper
import com.githubExamples.mvvm.architecture.utils.FILE_NOT_FOUND
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetCountryListUseCase(
        private val countryApiRepo: GetCountryListingFromApi,
        private val fileRepository: GetCountryListingFromLocal,
        private val gson: Gson
) : UseCase<UseCaseWrapper<DataWrapper>>() {

    override suspend fun requestForData(): UseCaseWrapper<DataWrapper> {
        try {
            //network call using retrofit
            val apiResponseRaw: CountryListResponse = countryApiRepo.getData()
            if (!apiResponseRaw.isNullOrEmpty()) {
                //sync to file storage if response is valid
                syncDataToLocal(apiResponseRaw)
            }
            val mappedApiResponse = mapRawDataToModelClass(apiResponseRaw)
            val dataWrapper = DataWrapper(mappedApiResponse, Source.NETWORK)
            //return successful data on  completion
            return UseCaseWrapper.Success(dataWrapper)
        } catch (ex: Exception) {
            //get data from file storage
            val cachedResponseRaw = fileRepository.getDataFromLocal()
            return if (cachedResponseRaw == FILE_NOT_FOUND) {
                UseCaseWrapper.Failed(ReasonToFail.SOMETHING_WENT_WRONG)
            } else {
                /* If the cached data is valid - deSerialize it to raw Api model
                 then map the data to presentation layer entity for UI consumption
                 */
                val cachedRawModelResponse = deSerializeCachedResponse(cachedResponseRaw)
                val cachedMappedResponse = mapRawDataToModelClass(cachedRawModelResponse)
                val dataWrapper = DataWrapper(cachedMappedResponse, Source.LOCAL)
                //return successful data on  completion
                UseCaseWrapper.Success(dataWrapper)
            }
        }
    }

    private suspend fun mapRawDataToModelClass(response: CountryListResponse): List<CountryItem> {
        return withContext(Dispatchers.Default) {
            response.map { eachCountryItemRaw ->
                CountryItemMapper().mapFrom(eachCountryItemRaw)
            }.apply {
                ArrayList<CountryItem>().addAll(this)
            }
        }

    }


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


