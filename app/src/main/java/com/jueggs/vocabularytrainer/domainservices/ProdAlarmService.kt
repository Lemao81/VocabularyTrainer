package com.jueggs.vocabularytrainer.domainservices

import android.app.AlarmManager.INTERVAL_DAY
import android.app.AlarmManager.RTC
import android.app.PendingIntent
import android.content.Context
import com.jueggs.andutils.extension.pendingBroadcastIntentFor
import com.jueggs.vocabularytrainer.Constant
import com.jueggs.vocabularytrainer.broadcastreceivers.DailyLearnNotificationReceiver
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAlarmService
import org.jetbrains.anko.alarmManager
import org.joda.time.DateTime

class ProdAlarmService : IAlarmService {

    override fun scheduleDailyLearnAlarm(context: Context) {
        val earlyAlarmTime = DateTime().withTime(Constant.NOTIFICATION_HOUR_OF_DAY_EARLY, 0, 0, 0)
        val lateAlarmTime = DateTime().withTime(Constant.NOTIFICATION_HOUR_OF_DAY_LATE, 0, 0, 0)

        val triggerTime = when {
            earlyAlarmTime.isAfterNow -> earlyAlarmTime
            lateAlarmTime.isAfterNow -> lateAlarmTime
            else -> earlyAlarmTime.plusDays(1)
        }
        context.alarmManager.setRepeating(
            RTC,
            triggerTime.millis,
            INTERVAL_DAY,
            context.pendingBroadcastIntentFor<DailyLearnNotificationReceiver>(PendingIntent.FLAG_UPDATE_CURRENT)
        )
    }
}