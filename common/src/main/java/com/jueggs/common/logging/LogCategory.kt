package com.jueggs.common.logging

enum class LogCategory {
    NOTIFICATION,
    DATABASE;

    override fun toString() = "MY${super.toString()}"
}