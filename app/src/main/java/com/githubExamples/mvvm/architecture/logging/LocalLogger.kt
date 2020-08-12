package com.githubExamples.mvvm.architecture.logging

class LocalLogger constructor(  val logger: Logger) : Logger {
    override fun log(type: Logger.LogType, tag: String, message: String) {
        if (type == Logger.LogType.LOG_LOCAL) {
            println("logging local")
        } else {
            logger.log(type, tag, message)
        }
    }


}