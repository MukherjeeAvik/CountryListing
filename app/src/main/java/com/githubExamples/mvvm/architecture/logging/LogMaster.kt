package com.githubExamples.mvvm.architecture.logging

class  LogMaster  ( val logger: Logger) {

    var logtype = Logger.LogType.LOG_REMOTE
    fun withLogType(type: Logger.LogType) {
        logtype = type
    }

    public fun log(tag: String, message: String) {
        logger.log(logtype, tag, message)
    }


}