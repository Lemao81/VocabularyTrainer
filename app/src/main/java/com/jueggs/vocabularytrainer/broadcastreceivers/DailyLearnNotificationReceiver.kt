package com.jueggs.vocabularytrainer.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jueggs.andutils.extension.isInForeground
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.jutils.logging.LogCategory
import com.jueggs.jutils.logging.Logger
import com.jueggs.vocabularytrainer.helper.DailyLearnNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.koin.core.KoinComponent
import org.koin.core.inject

class DailyLearnNotificationReceiver : BroadcastReceiver(), KoinComponent {
    val flashCardRepository by inject<IFlashCardRepository>()
    val flashCardBoxService by inject<IFlashCardBoxService>()

    override fun onReceive(context: Context, intent: Intent) {
        val now = DateTime.now()
        GlobalScope.launch {
            val isSomethingToLearn = FlashCardBox.values().any {
                flashCardRepository.readByBoxAndExpiryDate(it, flashCardBoxService.getBoxExpiryDate(it, now).millis).any()
            }
            Logger.newEntry(if (isSomethingToLearn) "something is to learn" else "nothing is to learn").withCategory(LogCategory.NOTIFICATION).logInfo()
            if (isSomethingToLearn && !context.isInForeground) {
                DailyLearnNotification.notify(context)
            }
        }
    }
}