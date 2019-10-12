package com.jueggs.commonj.logging

interface ILogTarget {
    fun log(entry: ILogEntry)

    suspend fun logAsync(entry: ILogEntry)
}