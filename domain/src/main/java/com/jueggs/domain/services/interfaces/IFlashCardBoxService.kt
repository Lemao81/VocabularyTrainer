package com.jueggs.domain.services.interfaces

import com.jueggs.domain.enums.FlashCardBox
import org.joda.time.DateTime

interface IFlashCardBoxService {
    fun getBoxExpiryDate(flashCardBox: FlashCardBox, now: DateTime): DateTime
    fun getNextBox(flashCardBox: FlashCardBox): FlashCardBox
}