package com.githubExamples.mvvm.architecture.logging


class RemoteLogger  constructor() : Logger {

    override fun log(type: Logger.LogType, tag: String, message: String) {

        if (type == Logger.LogType.LOG_REMOTE) {
            println("logging remote")
        } else {

        }
    }
}

