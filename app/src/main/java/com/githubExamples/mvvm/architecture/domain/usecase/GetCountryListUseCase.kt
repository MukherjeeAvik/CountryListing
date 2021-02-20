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

        return withContext(Dispatchers.IO) {

            try {
                val apiResponseRaw: CountryListResponse = countryApiRepo.getData()
                if (!apiResponseRaw.isNullOrEmpty()) {
                    syncDataToLocal(apiResponseRaw)
                }
                val mappedApiResponse = mapRawDataToModelClass(apiResponseRaw)
                val dataWrapper = DataWrapper(mappedApiResponse, Source.NETWORK)
                UseCaseWrapper.Success(dataWrapper)
            } catch (ex: Exception) {
                ex.printStackTrace()
                val cachedResponseRaw = fileRepository.getDataFromLocal()
                if (cachedResponseRaw == FILE_NOT_FOUND) {
                    UseCaseWrapper.Failed(ReasonToFail.SOMETHING_WENT_WRONG)
                } else {
                    val cachedRawModelResponse =
                        gson.fromJson(cachedResponseRaw, CountryListResponse::class.java)
                    val cachedMappedResponse =
                        mapRawDataToModelClass(cachedRawModelResponse)
                    val dataWrapper = DataWrapper(cachedMappedResponse, Source.LOCAL)
                    UseCaseWrapper.Success(dataWrapper)
                }
            }
        }
    }

    private  fun mapRawDataToModelClass(
        response: CountryListResponse
    ): List<CountryItem> {

        val mappedListOfCountries = ArrayList<CountryItem>()

       response.map { eachCountryItemRaw ->
            CountryItemMapper().mapFrom(eachCountryItemRaw)
        }.apply {
            mappedListOfCountries.addAll(this)
        }

        return mappedListOfCountries

    }


    private suspend fun syncDataToLocal(networkResponse: CountryListResponse) {
        val content = gson.toJson(networkResponse)
        fileRepository.saveDataToLocal(content)

    }

}


