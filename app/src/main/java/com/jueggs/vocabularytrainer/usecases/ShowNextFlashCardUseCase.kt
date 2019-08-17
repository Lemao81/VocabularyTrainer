package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.jutils.Util
import com.jueggs.jutils.extension.join
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService,
    private val json: Json
) : ViewStateUseCaseWithParameter<LearnViewState, LearnViewModel> {
    @ImplicitReflectionSerializer
    override suspend fun invoke(param: LearnViewModel): StateEvent<LearnViewState> {
        val now = DateTime.now()
        var nextCard: FlashCardEntity? = null
        FlashCardBox.values().forEach { it ->
            val flashCards = flashCardRepository.readByBoxNumberAndExpiryDate(it.number, flashCardBoxService.getBoxExpiryDate(it, now))
            if (flashCards.any()) {
                nextCard = flashCards.sortedBy { it.lastLearnedDate }.first()

                return@forEach
            }
        }

        return if (nextCard != null) {
            param.currentFlashCardId = nextCard?.id
            val backSideTexts = if (nextCard?.backSideTexts != null) json.parse(nextCard?.backSideTexts!!) else emptyList<String>()

            Alter {
                copy(
                    frontSideText = nextCard?.frontSideText,
                    backSideText = backSideTexts.mapIndexed { index, t -> "${index + 1}.  $t" }.join(Util.lineSeparator),
                    isRevealed = false
                )
            }
        } else {
            Trigger { copy(navigationId = R.id.action_learnFragment_to_nothingToLearnFragment) }
        }
    }
}