package com.jueggs.vocabularytrainer.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAlarmService
import org.koin.android.ext.android.inject

class ScheduleDailyAlarmService : Service() {
    val alarmService by inject<IAlarmService>()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        alarmService.scheduleDailyLearnAlarm(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_NOT_STICKY
}