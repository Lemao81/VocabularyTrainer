package com.jueggs.vocabularytrainer.notifications

import android.app.Notification.DEFAULT_ALL
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import androidx.core.app.NotificationCompat
import com.jueggs.andutils.extension.createSettingsIntent
import com.jueggs.andutils.extension.pendingActivityIntent
import com.jueggs.andutils.extension.pendingActivityIntentFor
import com.jueggs.common.isEclairOrAbove
import com.jueggs.jutils.logging.LogCategory
import com.jueggs.jutils.logging.Logger
import com.jueggs.vocabularytrainer.MainActivity
import com.jueggs.vocabularytrainer.R
import org.jetbrains.anko.notificationManager

object DailyLearnNotification {
    private const val TAG = "DailyLearnNotification"
    const val CHANNEL_ID = "DailyLearnNotificationChannel"

    fun notify(context: Context) {
        val title = context.getString(R.string.daily_learn_check_notification_title)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_stat_daily_learn_check)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(context.getString(R.string.daily_learn_check_notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(context.pendingActivityIntentFor<MainActivity>(FLAG_ONE_SHOT))
            .addAction(createDisableNotificationsAction(context))
            .setAutoCancel(true)

        if (isEclairOrAbove()) {
            context.notificationManager.notify(TAG, 0, builder.build())
            Logger.newEntry("daily notification sent").withCategory(LogCategory.NOTIFICATION).logInfo()
        }
    }

    fun cancel(context: Context) {
        if (isEclairOrAbove()) {
            context.notificationManager.cancel(TAG, 0)
        }
    }

    private fun createDisableNotificationsAction(context: Context): NotificationCompat.Action {
        val builder = NotificationCompat.Action.Builder(
            R.drawable.ic_notifications_off_white_24dp,
            context.getString(R.string.action_disable_daily_notification),
            context.pendingActivityIntent(context.createSettingsIntent(), FLAG_ONE_SHOT)
        )

        return builder.build()
    }
}
