package com.jueggs.common.logging

import org.joda.time.DateTime

interface ILogEntry {
    val message: String
    val timestamp: DateTime
    val method: String
    val logLevel: LogLevel
    val category: String
    val valueMap: Map<String, Any>?
    val exception: Throwable?
}

