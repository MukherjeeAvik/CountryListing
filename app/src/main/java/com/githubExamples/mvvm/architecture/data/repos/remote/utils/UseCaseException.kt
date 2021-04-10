package com.githubExamples.mvvm.architecture.data.repos.remote.utils

import com.githubExamples.mvvm.architecture.domain.usecase.ReasonToFail
import java.lang.IllegalStateException

class UseCaseException(val reason: ReasonToFail) : IllegalStateException() {

    override val message: String
        get() = reason.toString()

}