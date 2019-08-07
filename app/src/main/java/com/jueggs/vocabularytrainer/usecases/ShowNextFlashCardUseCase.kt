package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.UseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.storagelayers.FlashCardDao
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardDao: FlashCardDao,
    private val flashCardBoxService: FlashCardBoxService
) : UseCase<LearnViewState> {
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
            Alter { copy(frontSideText = nextCard?.frontSideText) }
        } else {
            Trigger { copy(navigationId = R.id.action_learnFragment_to_nothingToLearnFragment) }
        }
    }
}