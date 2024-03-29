package com.jueggs.vocabularytrainer.helper

import android.app.Notification.DEFAULT_ALL
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.jueggs.andutils.extension.createSettingsIntent
import com.jueggs.andutils.extension.pendingActivityIntent
import com.jueggs.common.isOreoOrAbove
import com.jueggs.jutils.logging.LogCategory
import com.jueggs.jutils.logging.Logger
import com.jueggs.vocabularytrainer.R
import org.jetbrains.anko.notificationManager

object DailyLearnNotification {
    private const val CHANNEL_ID = "DailyLearnNotificationChannel"

    fun createChannelIfNeeded(context: Context) {
        if (isOreoOrAbove() && context.notificationManager.notificationChannels.none { it.id == CHANNEL_ID }) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.daily_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            context.notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun notify(context: Context) {
        val title = context.getString(R.string.daily_learn_check_notification_title)
        val contentIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.learnFragment)
            .createPendingIntent()
        val action = NotificationCompat.Action.Builder(
            R.drawable.ic_notifications_off_white_24dp,
            context.getString(R.string.action_disable_daily_notification),
            context.pendingActivityIntent(context.createSettingsIntent(), FLAG_ONE_SHOT)
        ).build()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_stat_daily_learn_check)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(context.getString(R.string.daily_learn_check_notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .addAction(action)
            .setAutoCancel(true)

        context.notificationManager.notify(0, builder.build())
        Logger.newEntry("daily notification sent").withCategory(LogCategory.NOTIFICATION).logInfo()
    }

    fun cancel(context: Context) = context.notificationManager.cancel(0)
}
