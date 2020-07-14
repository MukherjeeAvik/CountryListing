package com.evaluation.digitas.coutryList.domain

import android.os.Parcel
import android.os.Parcelable

data class CountryItem(
    val name: String,
    val capital: String,
    val population: String,
    val currency: List<String>,
    val latitude: String,
    val longitude: String,
    val borders: List<String>,
    val languages: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(capital)
        parcel.writeString(population)
        parcel.writeStringList(currency)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeStringList(borders)
        parcel.writeStringList(languages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryItem> {
        override fun createFromParcel(parcel: Parcel): CountryItem {
            return CountryItem(parcel)
        }

        override fun newArray(size: Int): Array<CountryItem?> {
            return arrayOfNulls(size)
        }
    }
}
