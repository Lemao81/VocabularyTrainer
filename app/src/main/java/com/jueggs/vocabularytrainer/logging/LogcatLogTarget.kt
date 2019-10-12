package com.jueggs.vocabularytrainer.logging

import android.util.Log
import com.jueggs.commonj.logging.ILogEntry
import com.jueggs.commonj.logging.ILogTarget
import com.jueggs.commonj.logging.LogLevel
import com.jueggs.domain.services.interfaces.ISerializer
import com.jueggs.jutils.extension.join
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogcatLogTarget(private val serializer: ISerializer) : ILogTarget {

    override fun log(entry: ILogEntry) {
        val messageParts = mutableListOf<String>()
        if (entry.message.isNotBlank()) {
            messageParts.add("Message: ${entry.message}")
        }
        if (entry.method.isNotBlank()) {
            messageParts.add("Method: ${entry.method}")
        }
        if (!entry.valueMap.isNullOrEmpty()) {
            entry.valueMap?.let { messageParts.add("Values: ${stringifyValues(it)}") }
        }
        val message = messageParts.join("  |  ")
        when (entry.logLevel) {
            LogLevel.DEBUG -> Log.d(entry.category, message, entry.exception)
            LogLevel.INFO -> Log.i(entry.category, message, entry.exception)
            LogLevel.WARNING -> Log.w(entry.category, message, entry.exception)
            LogLevel.ERROR -> Log.e(entry.category, message, entry.exception)
            LogLevel.FATAL -> Log.e(entry.category, message, entry.exception)
        }
    }

    override suspend fun logAsync(entry: ILogEntry) {
        GlobalScope.launch { log(entry) }
    }

    private fun stringifyValues(valueMap: Map<String, Any?>): String = serializer.toJson(valueMap, String::class).toString()
}