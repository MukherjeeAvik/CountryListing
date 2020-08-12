package com.githubExamples.mvvm.architecture.domain.usecase

sealed class UseCaseWrapper<out T> {
    data class Success<T>(val data: T) : UseCaseWrapper<T>()
    data class Failed<T>(val reason: ReasonToFail) : UseCaseWrapper<T>()

}

enum class ReasonToFail {
    SOMETHING_WENT_WRONG, NO_NETWORK_AVAILABLE
}

enum class Source {
    NETWORK, LOCAL
}
