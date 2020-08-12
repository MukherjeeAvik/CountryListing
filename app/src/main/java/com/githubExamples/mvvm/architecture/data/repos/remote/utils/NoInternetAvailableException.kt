package com.githubExamples.mvvm.architecture.data.repos.remote.utils

class NoInternetAvailableException : Exception() {
    
    override val message: String?
        get() = "No internet available"

}
