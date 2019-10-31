package com.jueggs.domain.usecases

import com.jueggs.domain.models.EditFlashCardData
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.EditFlashCardViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.jutils.validation.IValidator
import org.joda.time.DateTime

class UpdateFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val inputValidator: IValidator<FlashCardInputData, FlashCardInputValidationResult>
) : MultipleViewStatesUseCaseWithParameter<EditFlashCardViewState, EditFlashCardData>() {

    override suspend fun execute(param: EditFlashCardData) {
        param.flashCardId?.let {
            when (val validationResult = inputValidator(param.inputData)) {
                is Valid -> {
                    val flashCard = flashCardRepository.readById(it)
                    flashCard.frontSideText = param.inputData.frontSideText
                    flashCard.backSideTexts = param.inputData.backSideTexts
                    flashCard.lastLearnedDate = DateTime.now()
                    flashCardRepository.update(flashCard)
                    triggerViewState { copy(isShouldMessageCardUpdated = true, isShouldPopFragment = true) }
                }
                else -> triggerViewState { copy(inputValidationResult = validationResult) }
            }
        }
    }
}