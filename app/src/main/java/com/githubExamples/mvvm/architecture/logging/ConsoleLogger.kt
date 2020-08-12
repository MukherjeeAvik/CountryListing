package com.githubExamples.mvvm.architecture.logging


class ConsoleLogger constructor(val logger: Logger) : Logger {

    override fun log(type: Logger.LogType, tag: String, message: String) {
        if (type == Logger.LogType.LOG_CONSOLE) {
            println("logging console")
        } else {
            logger.log(type, tag, message)
        }
        throw IllegalAccessException()
    }

}