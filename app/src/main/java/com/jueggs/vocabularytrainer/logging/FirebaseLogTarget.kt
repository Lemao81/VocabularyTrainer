package com.jueggs.vocabularytrainer.logging

import com.jueggs.common.logging.ILogEntry
import com.jueggs.common.logging.ILogTarget

class FirebaseLogTarget : ILogTarget {
    override fun log(entry: ILogEntry) {
        TODO("not implemented")
    }

    override suspend fun logAsync(entry: ILogEntry) {
        TODO("not implemented")
    }
}