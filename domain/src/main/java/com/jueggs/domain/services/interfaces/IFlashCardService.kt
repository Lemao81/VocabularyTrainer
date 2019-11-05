package com.jueggs.domain.services.interfaces

import com.jueggs.domain.models.FlashCard
import org.joda.time.DateTime

interface IFlashCardService {
    fun isCardDueToLearn(flashCard: FlashCard, now: DateTime): Boolean
}