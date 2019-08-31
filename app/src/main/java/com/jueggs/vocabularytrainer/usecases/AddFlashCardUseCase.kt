package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.result.Invalid
import com.jueggs.andutils.usecase.IValidator
import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.ISerializer
import com.jueggs.common.interfaces.IFlashCardRepository
import com.jueggs.common.models.FlashCard
import com.jueggs.jutils.INVALID
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import org.joda.time.DateTime

class AddFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val inputValidator: IValidator<AddFlashCardData>,
    private val serializer: ISerializer
) : MultipleViewStatesUseCaseWithParameter<AddFlashCardViewState, AddFlashCardData>() {

    override suspend fun execute(param: AddFlashCardData) {
        when (val validationResult = inputValidator(param)) {
            is Invalid -> triggerViewState { copy(longMessageId = validationResult.resId) }
            else -> {
                val newFlashCard = FlashCard(
                    id = null,
                    frontSideText = param.frontSideText,
                    backSideTexts = serializer.stringify(param.backSideTexts.filterNot { it.isBlank() }, String::class),
                    box = FlashCardBox.ONE,
                    lastLearnedDate = DateTime.now()
                )
                flashCardRepository.insert(newFlashCard)
                alterViewState {
                    copy(focusedInputIndex = INVALID, backSideViewsShownUpToIndex = 0)
                }
                triggerViewState {
                    copy(shortMessageId = R.string.message_card_added, isShouldEmptyInputs = true, isShouldFocusFrontSideEdit = true)
                }
            }
        }
    }
}