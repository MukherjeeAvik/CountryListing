package com.evaluation.digitas.coutryList.repos

import io.reactivex.Observable

interface Repository<T> {
    fun getData(vararg params: Any): Observable<T>
}
