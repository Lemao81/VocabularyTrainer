package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.result.Invalid
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.jutils.INVALID
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.validators.AddFlashCardInputValidator
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import org.joda.time.DateTime

class AddFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository,
    private val json: Json,
    private val addFlashCardInputValidator: AddFlashCardInputValidator
) : ViewStateUseCaseWithParameter<AddFlashCardViewState, AddFlashCardData> {
    @ImplicitReflectionSerializer
    override suspend fun invoke(param: AddFlashCardData): StateEvent<AddFlashCardViewState> {
        val validationResult = addFlashCardInputValidator(param)
        if (validationResult is Invalid) {
            return Trigger { copy(longMessageId = validationResult.resId) }
        }

        val newFlashCard = FlashCardEntity(
            id = null,
            frontSideText = param.frontSideText,
            backSideTexts = json.stringify(param.backSideTexts.filterNot { it.isBlank() }),
            boxNumber = FlashCardBox.ONE.number,
            lastLearnedDate = DateTime.now().millis
        )
        flashCardRepository.insert(newFlashCard)
        param.addFlashCardViewModel.frontSideText.postValue("")
        param.addFlashCardViewModel.backSideTexts.forEach { it.postValue("") }

        return Trigger {
            copy(
                shortMessageId = R.string.message_card_added,
                focusedInputIndex = INVALID,
                backSideViewsShownUpToIndex = 0
            )
        }
    }
}