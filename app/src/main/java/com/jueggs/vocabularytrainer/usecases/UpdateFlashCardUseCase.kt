package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.result.Invalid
import com.jueggs.andutils.usecase.IValidator
import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.interfaces.IFlashCardRepository
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.EditFlashCardData
import com.jueggs.vocabularytrainer.models.FlashCardInputData
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState

class UpdateFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val inputValidator: IValidator<FlashCardInputData>
) : MultipleViewStatesUseCaseWithParameter<AddFlashCardViewState, EditFlashCardData>() {

    override suspend fun execute(param: EditFlashCardData) {
        param.flashCardId?.let {
            when (val validationResult = inputValidator(param.inputData)) {
                is Invalid -> triggerViewState { copy(longMessageId = validationResult.resId) }
                else -> {
                    val flashCard = flashCardRepository.readById(it)
                    flashCard.frontSideText = param.inputData.frontSideText
                    flashCard.backSideTexts = param.inputData.backSideTexts
                    flashCardRepository.update(flashCard)
                    triggerViewState { copy(shortMessageId = R.string.message_card_updated, isShouldPopFragment = true) }
                }
            }
        }
    }
}