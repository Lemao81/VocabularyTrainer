package com.jueggs.vocabularytrainer.domainservices.interfaces

import android.content.Context

interface IAlarmService {
    fun scheduleDailyLearnAlarm(context: Context)
}