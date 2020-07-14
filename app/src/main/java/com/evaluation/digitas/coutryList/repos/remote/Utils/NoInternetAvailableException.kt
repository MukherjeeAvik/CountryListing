package com.evaluation.digitas.coutryList.repos.remote.Utils

class NoInternetAvailableException : Exception() {
    
    override val message: String?
        get() = "No internet available"

}
