package com.githubExamples.mvvm.architecture.domain.usecase

abstract class UseCase<T> {

    abstract suspend fun requestForData():T


}
