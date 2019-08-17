package com.jueggs.common.services

import com.jueggs.common.enums.FlashCardBox
import org.joda.time.DateTime

class FlashCardBoxService {
    fun getBoxExpiryDate(flashCardBox: FlashCardBox, now: DateTime) = now.minusMinutes(flashCardBox.latency).millis
}