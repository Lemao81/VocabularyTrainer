package com.jueggs.common.logging

enum class LogCategory {
    NOTIFICATION,
    DATABASE,
    UNHANDLEDEXCEPTION;

    override fun toString() = "MY${super.toString()}"
}