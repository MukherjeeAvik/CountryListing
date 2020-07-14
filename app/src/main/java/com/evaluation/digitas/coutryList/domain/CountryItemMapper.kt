package com.evaluation.digitas.coutryList.domain

import com.evaluation.digitas.coutryList.repos.remote.models.CountryListResponseItem
import com.evaluation.digitas.coutryList.utils.INCONSISTENT_VALUE

class CountryItemMapper : Mapper<CountryListResponseItem, CountryItem> {
    override fun mapFrom(k: CountryListResponseItem): CountryItem {
        var _latitude = ""
        var _longitude = ""
        k.latlng?.let {
            if (it.isNotEmpty()) {
                _latitude = (k.latlng?.get(0) ?: INCONSISTENT_VALUE).toString()
            }
            if (it.size > 1)
                _longitude = (k.latlng?.get(1) ?: INCONSISTENT_VALUE).toString()
        }
        return CountryItem(
            name = k.name ?: "",
            capital = k.capital ?: "",
            population = (k.population ?: INCONSISTENT_VALUE).toString(),
            currency = k.currencies ?: ArrayList(),
            latitude = _latitude,
            longitude = _longitude,
            borders = k.borders ?: ArrayList(),
            languages = k.languages ?: ArrayList()
        )
    }

}
