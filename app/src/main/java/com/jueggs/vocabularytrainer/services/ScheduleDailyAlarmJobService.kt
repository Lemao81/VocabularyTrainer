package com.jueggs.vocabularytrainer.services

import android.app.job.JobParameters
import android.app.job.JobService
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAlarmService
import org.koin.android.ext.android.inject

class ScheduleDailyAlarmJobService : JobService() {
    val alarmService by inject<IAlarmService>()

    override fun onStartJob(params: JobParameters?): Boolean {
        alarmService.scheduleDailyLearnAlarm(this)

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}