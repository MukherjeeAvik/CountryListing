package com.githubExamples.mvvm.architecture.domain.usecase

sealed class UseCaseWrapper<out T> {
    data class Success<T>(val data: T) : UseCaseWrapper<T>()
    data class Failed<T>(val reason: ReasonToFail) : UseCaseWrapper<T>()

}

enum class ReasonToFail(val value: String) {
    SOMETHING_WENT_WRONG("Something is not right here!"),
    NO_NETWORK_AVAILABLE("No network is available at the moment!"),
    INVALID_API_RESPONSE("There seems to a technical problem we are facing!")
}

enum class Source {
    NETWORK, LOCAL
}
