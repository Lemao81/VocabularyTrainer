package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class DismissWrongFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<LearnViewState, Long?>() {

    override suspend fun execute(param: Long?) {
        param?.let {
            val flashCard = flashCardRepository.readById(it)
            flashCard.boxNumber = FlashCardBox.ONE.number
            flashCard.lastLearnedDate = DateTime.now().millis
            flashCardRepository.update(flashCard)
            alterViewState { copy(currentFlashCardId = null) }
        }
    }
}