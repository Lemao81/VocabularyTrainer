package com.jueggs.vocabularytrainer.logging

import android.content.Context
import com.jueggs.commonj.logging.ILogEntry
import com.jueggs.commonj.logging.ILogTarget
import com.jueggs.database.ILogDao
import com.jueggs.database.LogDatabase
import com.jueggs.database.LogEntryEntity
import com.jueggs.domain.services.interfaces.ISerializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomLogTarget(private val serializer: ISerializer, val context: Context) : ILogTarget {
    private var logDao: ILogDao = LogDatabase.getInstance(context).getLogDao()

    override fun log(entry: ILogEntry) {
        val entryEntity = LogEntryEntity(
            category = entry.category,
            logLevel = entry.logLevel.toString(),
            method = entry.method,
            message = entry.message,
            timestampString = entry.timestamp.toString(),
            timestampMillis = entry.timestamp.millis,
            exception = entry.exception?.toString(),
            values = entry.valueMap?.let { stringifyValues(it) }
        )

        logDao.insert(entryEntity)
    }

    override suspend fun logAsync(entry: ILogEntry) {
        GlobalScope.launch { log(entry) }
    }

    private fun stringifyValues(valueMap: Map<String, Any?>): String = serializer.toJson(valueMap, String::class).toString()
}