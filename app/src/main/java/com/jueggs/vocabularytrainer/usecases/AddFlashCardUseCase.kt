package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.result.Invalid
import com.jueggs.andutils.usecase.IValidator
import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.IFlashCardRepository
import com.jueggs.common.models.FlashCard
import com.jueggs.jutils.INVALID
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.models.FlashCardInputData
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import org.joda.time.DateTime

class AddFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val inputValidator: IValidator<FlashCardInputData>
) : MultipleViewStatesUseCaseWithParameter<AddFlashCardViewState, AddFlashCardData>() {

    override suspend fun execute(param: AddFlashCardData) {
        when (val validationResult = inputValidator(param.inputData)) {
            is Invalid -> triggerViewState { copy(longMessageId = validationResult.resId) }
            else -> {
                val newFlashCard = FlashCard(
                    id = null,
                    frontSideText = param.inputData.frontSideText,
                    backSideTexts = param.inputData.backSideTexts,
                    box = FlashCardBox.ONE,
                    lastLearnedDate = DateTime.now()
                )
                flashCardRepository.insert(newFlashCard)
                triggerViewState { copy(shortMessageId = R.string.message_card_added) }
                if (param.isKeepAdding == true) {
                    alterViewState {
                        copy(focusedInputIndex = INVALID, backSideViewsShownUpToIndex = 0)
                    }
                    triggerViewState {
                        copy(isShouldEmptyInputs = true, isShouldFocusFrontSideEdit = true)
                    }
                } else {
                    triggerViewState { copy(isShouldPopFragment = true) }
                }
            }
        }
    }
}