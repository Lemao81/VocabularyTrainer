package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.UseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.storagelayers.FlashCardDao
import com.jueggs.jutils.extension.join
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardDao: FlashCardDao,
    private val flashCardBoxService: FlashCardBoxService,
    private val json: Json
) : UseCase<LearnViewState> {
    @ImplicitReflectionSerializer
    override suspend fun invoke(): StateEvent<LearnViewState> {
        val now = DateTime()
        var nextCard: FlashCardEntity? = null
        FlashCardBox.values().forEach { it ->
            val flashCards = flashCardDao.readByBoxNumberAndExpiryDate(it.id, flashCardBoxService.getBoxExpiryDate(it, now))
            if (flashCards.any()) {
                nextCard = flashCards.sortedBy { it.lastLearnedDate }.first()

                return@forEach
            }
        }

        return if (nextCard != null) {
            val backSideTexts = if (nextCard?.backSideTexts != null) json.parse(nextCard?.backSideTexts!!) else emptyList<String>()
            val lineSeparator = System.getProperty("line.separator") ?: "\n"
            Alter { copy(frontSideText = nextCard?.frontSideText, backSideText = backSideTexts.join(lineSeparator)) }   // TODO lib
        } else {
            Trigger { copy(navigationId = R.id.action_learnFragment_to_nothingToLearnFragment) }
        }
    }
}