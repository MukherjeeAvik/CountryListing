package com.githubExamples.mvvm.architecture.domain.usecase

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class UseCase<T> {


    protected val dataRepo = PublishSubject.create<T>()
    protected val compositeDisposable by lazy { CompositeDisposable() }


    abstract fun subscribeForData(): Observable<T>
    fun unsubscribeFromDataSource() {
        compositeDisposable.clear()
    }



}
