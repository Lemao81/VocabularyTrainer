package com.jueggs.domain.services

import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardService
import org.joda.time.DateTime

class FlashCardService(
    private val flashCardBoxService: IFlashCardBoxService
) : IFlashCardService {
    override fun isCardDueToLearn(flashCard: FlashCard, now: DateTime): Boolean {
        val expiryDate = flashCardBoxService.getBoxExpiryDate(flashCard.box, now)

        return expiryDate.isAfter(flashCard.lastLearnedDate)
    }
}