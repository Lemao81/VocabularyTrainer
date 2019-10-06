package com.jueggs.common.logging

interface ILogEntryBuilder {
    fun addValue(name: String, obj: Any): ILogEntryBuilder

    fun withCategory(category: String): ILogEntryBuilder

    fun withCategory(category: LogCategory): ILogEntryBuilder

    fun withException(exception: Throwable): ILogEntryBuilder

    fun logDebug()

    suspend fun logDebugAsync()

    fun logInfo()

    suspend fun logInfoAsync()

    fun logWarning()

    suspend fun logWarningAsync()

    fun logError()

    suspend fun logErrorAsync()

    fun logFatal()

    suspend fun logFatalAsync()
}