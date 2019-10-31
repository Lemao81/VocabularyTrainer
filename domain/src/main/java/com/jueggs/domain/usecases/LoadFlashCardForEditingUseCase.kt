package com.jueggs.domain.usecases

import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.EditFlashCardViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter

class LoadFlashCardForEditingUseCase(
    private val flashCardRepository: IFlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<EditFlashCardViewState, Long>() {

    override suspend fun execute(param: Long) {
        val flashCard = flashCardRepository.readById(param)
        val backsideTextCount = flashCard.backSideTexts.size
        triggerViewState {
            copy(
                frontSideText = flashCard.frontSideText,
                backSideText1 = if (backsideTextCount > 0) flashCard.backSideTexts[0] else "",
                backSideText2 = if (backsideTextCount > 1) flashCard.backSideTexts[1] else "",
                backSideText3 = if (backsideTextCount > 2) flashCard.backSideTexts[2] else "",
                backSideText4 = if (backsideTextCount > 3) flashCard.backSideTexts[3] else "",
                isShouldClearFocus = true
            )
        }
    }
}