package com.githubExamples.mvvm.architecture.domain

import io.reactivex.Completable
import io.reactivex.Observable

interface Repository<T>  {
    fun saveData(data: T): Completable
    fun getData(vararg params:Any): Observable<T>
}
