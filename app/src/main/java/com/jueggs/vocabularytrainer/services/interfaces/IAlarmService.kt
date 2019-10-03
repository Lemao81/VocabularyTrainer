package com.jueggs.vocabularytrainer.services.interfaces

import android.content.Context

interface IAlarmService {
    fun scheduleDailyLearnAlarm(context: Context)
}