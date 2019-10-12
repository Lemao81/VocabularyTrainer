package com.jueggs.vocabularytrainer.logging

import com.jueggs.commonj.logging.ILogEntry
import com.jueggs.commonj.logging.ILogTarget

class RoomLogTarget : ILogTarget {
    override fun log(entry: ILogEntry) {
        TODO("not implemented")
    }

    override suspend fun logAsync(entry: ILogEntry) {
        TODO("not implemented")
    }
}