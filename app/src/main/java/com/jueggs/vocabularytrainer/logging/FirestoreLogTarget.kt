package com.jueggs.vocabularytrainer.logging

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.jueggs.commonj.logging.ILogEntry
import com.jueggs.commonj.logging.ILogTarget
import com.jueggs.domain.services.interfaces.ISerializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class FirestoreLogTarget(private val serializer: ISerializer, val context: Context) : ILogTarget {
    init {
        FirebaseApp.initializeApp(context)
    }

    override fun log(entry: ILogEntry) {
        val packageName = context.packageName
        val map = hashMapOf(
            "packagename" to packageName,
            "category" to entry.category,
            "exception" to entry.exception?.toString(),
            "logLevel" to entry.logLevel.toString(),
            "message" to entry.message,
            "method" to entry.method,
            "timestamp" to Date(entry.timestamp.millis),
            "values" to entry.valueMap?.let { stringifyValues(it) }
        )

        val appName = packageName.substring(packageName.lastIndexOf('.'))
        FirebaseFirestore.getInstance().collection("logEntries").document("${appName}_${entry.timestamp}").set(map)
    }

    override suspend fun logAsync(entry: ILogEntry) {
        GlobalScope.launch { log(entry) }
    }

    private fun stringifyValues(valueMap: Map<String, Any?>): String = serializer.toJson(valueMap, String::class).toString()
}