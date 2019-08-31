package com.jueggs.common.services

import com.jueggs.common.enums.FlashCardBox
import org.joda.time.DateTime

class FlashCardBoxService {
    fun getBoxExpiryDate(flashCardBox: FlashCardBox, now: DateTime) = now.minusDays(flashCardBox.latency).millis

    fun getNextBox(flashCardBox: FlashCardBox): FlashCardBox = FlashCardBox.values().single { it.number == Math.min(flashCardBox.number + 1, FlashCardBox.values().size) }
}