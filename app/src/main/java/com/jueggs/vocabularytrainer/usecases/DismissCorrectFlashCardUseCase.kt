package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.models.DismissCorrectFlashCardData
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class DismissCorrectFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<LearnViewState, DismissCorrectFlashCardData>() {

    override suspend fun execute(param: DismissCorrectFlashCardData) {
        param.currentFlashCardId?.let { id ->
            val flashCard = flashCardRepository.readById(id)
            val isLastBox = flashCard.boxNumber == FlashCardBox.values().maxBy { it.number }?.number
            if (isLastBox) {
                flashCardRepository.delete(flashCard)
                val message = String.format(param.cardLearnedMessageFormat, flashCard.frontSideText)
                triggerViewState { copy(longMessage = message) }
            } else {
                flashCard.boxNumber++
                flashCard.lastLearnedDate = DateTime.now().millis
                flashCardRepository.update(flashCard)
            }
        }
    }
}