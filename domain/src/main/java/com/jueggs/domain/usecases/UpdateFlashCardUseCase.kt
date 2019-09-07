package com.jueggs.domain.usecases

import com.jueggs.domain.interfaces.IFlashCardRepository
import com.jueggs.domain.models.EditFlashCardData
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid
import com.jueggs.domain.viewstates.AddFlashCardViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.jutils.validation.IValidator

class UpdateFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val inputValidator: IValidator<FlashCardInputData, FlashCardInputValidationResult>
) : MultipleViewStatesUseCaseWithParameter<AddFlashCardViewState, EditFlashCardData>() {

    override suspend fun execute(param: EditFlashCardData) {
        param.flashCardId?.let {
            when (val validationResult = inputValidator(param.inputData)) {
                is Valid -> {
                    val flashCard = flashCardRepository.readById(it)
                    flashCard.frontSideText = param.inputData.frontSideText
                    flashCard.backSideTexts = param.inputData.backSideTexts
                    flashCardRepository.update(flashCard)
                    triggerViewState { copy(isShouldMessageCardUpdated = true, isShouldPopFragment = true) }
                }
                else -> triggerViewState { copy(inputValidationResult = validationResult) }
            }
        }
    }
}