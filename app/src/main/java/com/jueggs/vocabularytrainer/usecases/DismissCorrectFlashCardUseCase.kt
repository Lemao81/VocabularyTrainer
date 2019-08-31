package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.FlashCardRepository
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.vocabularytrainer.models.DismissCorrectFlashCardData
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class DismissCorrectFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService
) : MultipleViewStatesUseCaseWithParameter<LearnViewState, DismissCorrectFlashCardData>() {

    override suspend fun execute(param: DismissCorrectFlashCardData) {
        param.currentFlashCardId?.let { id ->
            val flashCard = flashCardRepository.readById(id)
            val isLastBox = flashCard.box == FlashCardBox.values().maxBy { it.number }
            if (isLastBox) {
                flashCardRepository.delete(flashCard)
                val message = String.format(param.cardLearnedMessageFormat, flashCard.frontSideText)
                triggerViewState { copy(longMessage = message) }
            } else {
                flashCard.box = flashCardBoxService.getNextBox(flashCard.box)
                flashCard.lastLearnedDate = DateTime.now()
                flashCardRepository.update(flashCard)
            }
        }
    }
}