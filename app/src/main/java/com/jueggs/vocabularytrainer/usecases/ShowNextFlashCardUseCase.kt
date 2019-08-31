package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.Serializer
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.jutils.Util
import com.jueggs.jutils.extension.join
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService,
    private val serializer: Serializer
) : MultipleViewStatesUseCase<LearnViewState>() {

    override suspend fun execute() {
        val now = DateTime.now()
        var nextCard: FlashCardEntity? = null
        FlashCardBox.values().forEach { it ->
            val dueFlashCards = flashCardRepository.readByBoxNumberAndExpiryDate(it.number, flashCardBoxService.getBoxExpiryDate(it, now))
            if (dueFlashCards.any()) {
                nextCard = dueFlashCards.minBy { it.lastLearnedDate }

                return@forEach
            }
        }

        if (nextCard != null) {
            val backSideTexts = if (nextCard?.backSideTexts != null) {
                serializer.parseList(nextCard?.backSideTexts!!, String::class)
            } else {
                emptyList()
            }
            val cardBackgroundColor = when (nextCard?.boxNumber) {
                FlashCardBox.TWO.number -> R.color.box2_background
                FlashCardBox.THREE.number -> R.color.box3_background
                FlashCardBox.FOUR.number -> R.color.box4_background
                FlashCardBox.FIVE.number -> R.color.box5_background
                FlashCardBox.SIX.number -> R.color.box6_background
                else -> R.color.box1_background
            }

            alterViewState {
                copy(
                    frontSideText = nextCard?.frontSideText,
                    backSideText = backSideTexts.mapIndexed { index, t -> "${index + 1}.  $t" }.join(Util.lineSeparator),
                    currentFlashCardId = nextCard?.id,
                    isRevealed = false,
                    cardBackgroundColorId = cardBackgroundColor
                )
            }
        } else {
            triggerViewState { copy(navigationId = R.id.action_learnFragment_to_nothingToLearnFragment) }
        }
    }
}