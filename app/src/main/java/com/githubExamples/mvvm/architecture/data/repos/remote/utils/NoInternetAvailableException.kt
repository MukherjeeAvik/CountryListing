package com.githubExamples.mvvm.architecture.data.repos.remote.utils

import java.io.IOException

class NoInternetAvailableException : IOException() {

    override val message: String
        get() = "No internet available"

}
