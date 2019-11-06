package com.jueggs.vocabularytrainer.broadcastreceivers

import android.app.job.JobInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.jueggs.andutils.extension.jobScheduler
import com.jueggs.andutils.receiver.AbstractBootCompletedReceiver
import com.jueggs.common.isOreoOrAbove
import com.jueggs.vocabularytrainer.services.ScheduleDailyAlarmJobService
import com.jueggs.vocabularytrainer.services.ScheduleDailyAlarmService
import org.jetbrains.anko.intentFor

class BootCompletedReceiver : AbstractBootCompletedReceiver() {
    override fun onBootCompleted(context: Context, intent: Intent) {
        if (isOreoOrAbove()) {
            val jobInfoBuilder = JobInfo.Builder(0, ComponentName(context, ScheduleDailyAlarmJobService::class.java))
            context.jobScheduler.schedule(jobInfoBuilder.build())
        } else {
            context.startService(context.intentFor<ScheduleDailyAlarmService>())
        }
    }
}