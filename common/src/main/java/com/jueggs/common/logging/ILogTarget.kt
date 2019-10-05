package com.jueggs.common.logging

interface ILogTarget {
    fun log(entry: ILogEntry)

    suspend fun logAsync(entry: ILogEntry)
}