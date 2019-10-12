package com.jueggs.commonj.logging

interface ILogManager {
    val targets: List<ILogTarget>

    fun newEntry(message: String = ""): ILogEntryBuilder
}

