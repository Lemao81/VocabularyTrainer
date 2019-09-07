package com.jueggs.domain.interfaces

import com.jueggs.domain.enums.FlashCardBox
import org.joda.time.DateTime

interface IFlashCardBoxService {
    fun getBoxExpiryDate(flashCardBox: FlashCardBox, now: DateTime): Long
    fun getNextBox(flashCardBox: FlashCardBox): FlashCardBox
}