package com.githubExamples.mvvm.architecture.logging

interface Logger {

    fun log(type: LogType, tag: String, message: String)

    enum class LogType {
        LOG_LOCAL, LOG_CONSOLE, LOG_REMOTE
    }
}