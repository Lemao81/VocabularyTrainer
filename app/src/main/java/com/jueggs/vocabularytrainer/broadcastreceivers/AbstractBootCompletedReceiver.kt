package com.jueggs.vocabularytrainer.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

abstract class AbstractBootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            onBootCompleted(context, intent)
        }
    }

    abstract fun onBootCompleted(context: Context, intent: Intent)
}