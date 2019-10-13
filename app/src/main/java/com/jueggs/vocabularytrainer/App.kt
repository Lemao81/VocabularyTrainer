package com.jueggs.vocabularytrainer

import android.app.NotificationChannel
import android.app.NotificationManager
import com.jueggs.andutils.base.BaseApplication
import com.jueggs.common.isOreoOrAbove
import com.jueggs.commonj.logging.LogCategory.UNHANDLEDEXCEPTION
import com.jueggs.commonj.logging.Logger
import com.jueggs.vocabularytrainer.domainservices.interfaces.IAlarmService
import com.jueggs.vocabularytrainer.notifications.DailyLearnNotification
import kotlinx.serialization.ImplicitReflectionSerializer
import org.jetbrains.anko.notificationManager
import org.koin.android.ext.android.inject

class App : BaseApplication(isDebug = BuildConfig.DEBUG) {
    val alarmService by inject<IAlarmService>()

    @ImplicitReflectionSerializer
    override fun koinModules() = listOf(mainKoinModule, buildVariantKoinModule)

    override fun initialize() {
        if (isOreoOrAbove() && notificationManager.notificationChannels.none { it.id == DailyLearnNotification.CHANNEL_ID }) {
            val notificationChannel = NotificationChannel(DailyLearnNotification.CHANNEL_ID, getString(R.string.daily_notification_channel_name), NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        alarmService.scheduleDailyLearnAlarm(this)
    }

    override fun onUncaughtException(exception: Throwable) {
        Logger.newEntry().withCategory(UNHANDLEDEXCEPTION).withException(exception).logFatal()
    }
}