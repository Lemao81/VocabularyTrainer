package com.jueggs.commonj.logging

import org.koin.core.KoinComponent
import org.koin.core.inject

object Logger : KoinComponent {
    private val logManager by inject<ILogManager>()

    fun newEntry(message: String = ""): ILogEntryBuilder = logManager.newEntry(message)
}