package com.jueggs.vocabularytrainer.services

import android.app.AlarmManager.INTERVAL_DAY
import android.app.AlarmManager.RTC
import android.app.PendingIntent
import android.content.Context
import com.jueggs.andutils.extension.pendingBroadcastIntentFor
import com.jueggs.vocabularytrainer.Constants
import com.jueggs.vocabularytrainer.broadcastreceivers.DailyLearnNotificationReceiver
import com.jueggs.vocabularytrainer.services.interfaces.IAlarmService
import org.jetbrains.anko.alarmManager
import org.joda.time.DateTime

class ProdAlarmService : IAlarmService {
    override fun scheduleDailyLearnAlarm(context: Context) {
        context.alarmManager.setRepeating(
            RTC,
            DateTime().withTime(Constants.NOTIFICATION_HOUR_OF_DAY, 0, 0, 0).millis,
            INTERVAL_DAY,
            context.pendingBroadcastIntentFor<DailyLearnNotificationReceiver>(PendingIntent.FLAG_UPDATE_CURRENT)
        )
    }
}