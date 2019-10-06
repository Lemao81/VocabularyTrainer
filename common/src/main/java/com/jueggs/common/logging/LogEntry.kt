package com.jueggs.common.logging

import org.joda.time.DateTime

class LogEntry(
    override val message: String,
    override val category: String,
    override val logLevel: LogLevel,
    override val timestamp: DateTime,
    override val method: String,
    override val exception: Throwable?,
    override val valueMap: Map<String, Any>?
) : ILogEntry {
    class Builder(
        private var message: String,
        private val logAction: (ILogEntry) -> Unit,
        private val logAsyncAction: suspend (ILogEntry) -> Unit
    ) : ILogEntryBuilder {
        private var category: String = "MYDEBUG"
        private var logLevel: LogLevel = LogLevel.DEBUG
        private var exception: Throwable? = null
        private var valueMap: MutableMap<String, Any>? = null

        override fun addValue(name: String, obj: Any): ILogEntryBuilder {
            if (valueMap == null) {
                valueMap = mutableMapOf()
            }
            valueMap?.put(name, obj)

            return this
        }

        override fun withCategory(category: String): ILogEntryBuilder {
            this.category = category

            return this
        }

        override fun withCategory(category: LogCategory): ILogEntryBuilder {
            this.category = category.toString()

            return this
        }

        override fun withException(exception: Throwable): ILogEntryBuilder {
            this.exception = exception

            return this
        }

        override fun logDebug() = executeLogAction(LogLevel.DEBUG)

        override suspend fun logDebugAsync() = executeLogAsyncAction(LogLevel.DEBUG)

        override fun logInfo() = executeLogAction(LogLevel.INFO)

        override suspend fun logInfoAsync() = executeLogAsyncAction(LogLevel.INFO)

        override fun logWarning() = executeLogAction(LogLevel.WARNING)

        override suspend fun logWarningAsync() = executeLogAsyncAction(LogLevel.WARNING)

        override fun logError() = executeLogAction(LogLevel.ERROR)

        override suspend fun logErrorAsync() = executeLogAsyncAction(LogLevel.ERROR)

        override fun logFatal() = executeLogAction(LogLevel.FATAL)

        override suspend fun logFatalAsync() = executeLogAsyncAction(LogLevel.FATAL)

        private fun executeLogAction(logLevel: LogLevel) {
            this.logLevel = logLevel
            logAction(createEntry())
        }

        private suspend fun executeLogAsyncAction(logLevel: LogLevel) {
            this.logLevel = logLevel
            logAsyncAction(createEntry())
        }

        private fun createEntry(): LogEntry {
            val stackElement = Thread.currentThread().stackTrace[5]
            val method = "${stackElement.className}.${stackElement.methodName}()"

            return LogEntry(
                message = message,
                category = category,
                logLevel = logLevel,
                timestamp = DateTime.now(),
                method = method,
                exception = exception,
                valueMap = valueMap
            )
        }
    }
}