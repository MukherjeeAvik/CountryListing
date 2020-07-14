package com.evaluation.digitas.coutryList.repos

import io.reactivex.Observable

interface LocalRepository<T>  {
    fun saveData(data: T)
    fun getDataFromLocal(): Observable<T>
}
