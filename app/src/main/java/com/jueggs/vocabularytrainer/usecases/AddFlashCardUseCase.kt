package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.result.Invalid
import com.jueggs.andutils.usecase.IValidator
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
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
) : ViewStateUseCaseWithParameter<AddFlashCardViewState, AddFlashCardData> {

    override suspend fun invoke(param: AddFlashCardData): StateEvent<AddFlashCardViewState> {
        when (val validationResult = inputValidator(param)) {
            is Invalid -> return Trigger { copy(longMessageId = validationResult.resId) }
            else -> {
                val newFlashCard = FlashCard(
                    id = null,
                    frontSideText = param.frontSideText,
                    backSideTexts = serializer.stringify(param.backSideTexts.filterNot { it.isBlank() }, String::class),
                    box = FlashCardBox.ONE,
                    lastLearnedDate = DateTime.now()
                )
                flashCardRepository.insert(newFlashCard)

                return Trigger {
                    copy(
                        shortMessageId = R.string.message_card_added,
                        focusedInputIndex = INVALID,
                        backSideViewsShownUpToIndex = 0,
                        isShouldEmptyInputs = true
                    )
                }
            }
        }
    }
}