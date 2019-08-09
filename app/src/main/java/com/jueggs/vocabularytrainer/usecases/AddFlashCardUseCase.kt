package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.storagelayers.FlashCardDao
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import org.joda.time.DateTime

class AddFlashCardUseCase(
    private val flashCardDao: FlashCardDao,
    private val json: Json
) : ViewStateUseCaseWithParameter<AddFlashCardViewState, AddFlashCardData> {
    @ImplicitReflectionSerializer
    override suspend fun invoke(param: AddFlashCardData): StateEvent<AddFlashCardViewState> {
        val backSideText = json.stringify(param.backSideTexts)
        val newFlashCard = FlashCardEntity(
            id = null,
            frontSideText = param.frontSideText,
            backSideTexts = backSideText,
            boxNumber = FlashCardBox.ONE.number,
            lastLearnedDate = DateTime.now().millis
        )
        flashCardDao.insert(newFlashCard)

        return Trigger { copy(isShouldPopFragment = true) }
    }
}