package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.interfaces.IFlashCardRepository
import com.jueggs.jutils.extension.maxIndex
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState

class LoadFlashCardForEditingUseCase(
    private val flashCardRepository: IFlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<AddFlashCardViewState, Long>() {

    override suspend fun execute(param: Long) {
        val flashCard = flashCardRepository.readById(param)
        val backsideTextCount = flashCard.backSideTexts.size
        alterViewState {
            copy(
                backSideViewsShownUpToIndex = flashCard.backSideTexts.maxIndex,
                isEditing = true
            )
        }
        triggerViewState {
            copy(
                frontSideText = flashCard.frontSideText,
                backSideText1 = if (backsideTextCount > 0) flashCard.backSideTexts[0] else "",
                backSideText2 = if (backsideTextCount > 1) flashCard.backSideTexts[1] else "",
                backSideText3 = if (backsideTextCount > 2) flashCard.backSideTexts[2] else "",
                backSideText4 = if (backsideTextCount > 3) flashCard.backSideTexts[3] else "",
                backSideText5 = if (backsideTextCount > 4) flashCard.backSideTexts[4] else "",
                isShouldClearFocus = true
            )
        }
    }
}