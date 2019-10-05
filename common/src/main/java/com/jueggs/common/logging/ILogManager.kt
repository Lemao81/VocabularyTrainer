package com.jueggs.common.logging

interface ILogManager {
    val targets: List<ILogTarget>

    fun newEntry(message: String = ""): ILogEntryBuilder
}

