package com.jueggs.commonj.logging

class LogManager(override val targets: List<ILogTarget>) : ILogManager {

    override fun newEntry(message: String): ILogEntryBuilder = LogEntry.Builder(
        message = message,
        logAction = { entry -> targets.forEach { it.log(entry) } },
        logAsyncAction = { entry -> targets.forEach { it.logAsync(entry) } }
    )
}