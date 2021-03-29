package com.githubExamples.mvvm.architecture.domain

interface GetCountryListingFromLocal {

    suspend fun saveDataToLocal(data:String)
    suspend fun getDataFromLocal():String
}