package com.jueggs.vocabularytrainer.services

import android.app.AlarmManager.RTC
import android.app.PendingIntent
import android.content.Context
import com.jueggs.andutils.d
import com.jueggs.andutils.extension.pendingBroadcastIntentFor
import com.jueggs.andutils.logging.Log
import com.jueggs.vocabularytrainer.broadcastreceivers.DailyLearnNotificationReceiver
import com.jueggs.vocabularytrainer.services.interfaces.IAlarmService
import org.jetbrains.anko.alarmManager
import org.joda.time.DateTime

class DevAlarmService : IAlarmService {
    override fun scheduleDailyLearnAlarm(context: Context) {
        d(Log.METHOD)
        context.alarmManager.setRepeating(
            RTC,
            DateTime.now().minusSeconds(30).millis,
            4 * 60 * 1000,
            context.pendingBroadcastIntentFor<DailyLearnNotificationReceiver>(PendingIntent.FLAG_UPDATE_CURRENT)
        )
    }
}