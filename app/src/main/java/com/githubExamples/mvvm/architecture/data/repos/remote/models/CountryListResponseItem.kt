package com.githubExamples.mvvm.architecture.data.repos.remote.models


import com.google.gson.annotations.SerializedName

data class CountryListResponseItem(
    @SerializedName("borders")
    var borders: List<String>?,
    @SerializedName("capital")
    var capital: String?,
    @SerializedName("currencies")
    var currencies: List<String>?,
    @SerializedName("languages")
    var languages: List<String>?,
    @SerializedName("latlng")
    var latlng: List<Double>?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("region")
    var region: String?,
    @SerializedName("population")
    var population: Int?
)
