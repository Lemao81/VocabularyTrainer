package com.jueggs.vocabularytrainer.broadcastreceivers

import android.content.Context
import android.content.Intent
import com.jueggs.vocabularytrainer.services.ScheduleDailyAlarmIntentService
import org.jetbrains.anko.intentFor

class BootCompletedReceiver : AbstractBootCompletedReceiver() {
    override fun onBootCompleted(context: Context, intent: Intent) {
        context.startService(context.intentFor<ScheduleDailyAlarmIntentService>())
    }
}