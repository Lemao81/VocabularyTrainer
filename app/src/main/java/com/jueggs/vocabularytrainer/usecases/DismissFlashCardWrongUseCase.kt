package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.UseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import org.joda.time.DateTime

class DismissFlashCardWrongUseCase(
    private val flashCardRepository: FlashCardRepository
) : UseCaseWithParameter<Long> {
    override suspend fun invoke(param: Long) {
        val flashCard = flashCardRepository.readById(param)
        flashCard.boxNumber = FlashCardBox.ONE.number
        flashCard.lastLearnedDate = DateTime.now().millis
        flashCardRepository.update(flashCard)
    }
}