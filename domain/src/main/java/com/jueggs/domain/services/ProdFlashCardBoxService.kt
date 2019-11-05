package com.jueggs.domain.services

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import org.joda.time.DateTime

class ProdFlashCardBoxService : IFlashCardBoxService {
    override fun getBoxExpiryDate(flashCardBox: FlashCardBox, now: DateTime): DateTime = now.minusDays(flashCardBox.latency).withHourOfDay(5)

    override fun getNextBox(flashCardBox: FlashCardBox): FlashCardBox =
        FlashCardBox.values().single { it.number == Math.min(flashCardBox.number + 1, FlashCardBox.values().size) }
}