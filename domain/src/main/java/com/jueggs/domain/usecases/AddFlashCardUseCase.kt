package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.models.AddFlashCardData
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid
import com.jueggs.domain.viewstates.AddFlashCardViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.jutils.validation.IValidator
import org.joda.time.DateTime

class AddFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val inputValidator: IValidator<FlashCardInputData, FlashCardInputValidationResult>
) : MultipleViewStatesUseCaseWithParameter<AddFlashCardViewState, AddFlashCardData>() {

    override suspend fun execute(param: AddFlashCardData) {
        when (val validationResult = inputValidator(param.inputData)) {
            is Valid -> {
                val newFlashCard = FlashCard(
                    id = null,
                    frontSideText = param.inputData.frontSideText,
                    backSideTexts = param.inputData.backSideTexts,
                    box = FlashCardBox.ONE,
                    lastLearnedDate = DateTime.now()
                )
                flashCardRepository.insert(newFlashCard)
                triggerViewState { copy(isShouldMessageCardAdded = true) }
                if (param.isKeepAdding == true) {
                    triggerViewState { copy(isShouldEmptyInputs = true, isShouldFocusFrontSideEdit = true) }
                } else {
                    triggerViewState { copy(isShouldPopFragment = true) }
                }
            }
            else -> triggerViewState { copy(inputValidationResult = validationResult) }
        }
    }
}