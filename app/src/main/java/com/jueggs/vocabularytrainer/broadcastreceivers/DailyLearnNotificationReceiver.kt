package com.jueggs.vocabularytrainer.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jueggs.andutils.d
import com.jueggs.andutils.logging.Log
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.vocabularytrainer.notifications.DailyLearnNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.koin.core.KoinComponent
import org.koin.core.inject

class DailyLearnNotificationReceiver : BroadcastReceiver(), KoinComponent {
    val flashCardRepository by inject<IFlashCardRepository>()
    val flashCardBoxService by inject<IFlashCardBoxService>()

    override fun onReceive(context: Context, intent: Intent) {
        d(Log.METHOD)
        val now = DateTime.now()
        GlobalScope.launch {
            val isSomethingToLearn = FlashCardBox.values().any {
                flashCardRepository.readByBoxAndExpiryDate(it, flashCardBoxService.getBoxExpiryDate(it, now)).any()
            }
            if (isSomethingToLearn) {
                DailyLearnNotification.notify(context)
            }
        }
    }
}