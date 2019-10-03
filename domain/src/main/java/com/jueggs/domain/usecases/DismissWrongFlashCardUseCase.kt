package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter
import org.joda.time.DateTime

class DismissWrongFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<LearnViewState, Long?>() {

    override suspend fun execute(param: Long?) {
        param?.let {
            val flashCard = flashCardRepository.readById(it)
            flashCard.box = FlashCardBox.ONE
            flashCard.lastLearnedDate = DateTime.now()
            flashCardRepository.update(flashCard)
            alterViewState { copy(currentFlashCardId = null) }
        }
    }
}