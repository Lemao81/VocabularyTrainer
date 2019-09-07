package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.interfaces.IFlashCardBoxService
import com.jueggs.domain.interfaces.IFlashCardRepository
import com.jueggs.domain.models.DismissCorrectFlashCardData
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter
import org.joda.time.DateTime

class DismissCorrectFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val flashCardBoxService: IFlashCardBoxService
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