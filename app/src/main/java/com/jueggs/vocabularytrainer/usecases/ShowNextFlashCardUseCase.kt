package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.ISerializer
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.common.interfaces.IFlashCardRepository
import com.jueggs.common.models.FlashCard
import com.jueggs.jutils.Util
import com.jueggs.jutils.extension.join
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService,
    private val serializer: ISerializer
) : MultipleViewStatesUseCase<LearnViewState>() {

    override suspend fun execute() {
        val now = DateTime.now()
        var nextCard: FlashCard? = null
        FlashCardBox.values().forEach { box ->
            val dueFlashCards = flashCardRepository.readByBoxNumberAndExpiryDate(box.number, flashCardBoxService.getBoxExpiryDate(box, now))
            if (dueFlashCards.any()) {
                nextCard = dueFlashCards.minBy { it.lastLearnedDate }

                return@forEach
            }
        }

        if (nextCard == null) {
            triggerViewState { copy(navigationId = R.id.action_learnFragment_to_nothingToLearnFragment) }
        }
        nextCard?.let { card ->
            val backSideTextList = serializer.parseList(card.backSideTexts, String::class)
            val backSideText = if (backSideTextList.size == 1) {
                backSideTextList.first()
            } else {
                backSideTextList.mapIndexed { index, t -> "${index + 1}.  $t" }.join(Util.lineSeparator)
            }
            val cardBackgroundColorId = when (card.box) {
                FlashCardBox.TWO -> R.color.box2_background
                FlashCardBox.THREE -> R.color.box3_background
                FlashCardBox.FOUR -> R.color.box4_background
                FlashCardBox.FIVE -> R.color.box5_background
                FlashCardBox.SIX -> R.color.box6_background
                else -> R.color.box1_background
            }

            alterViewState {
                copy(
                    frontSideText = card.frontSideText,
                    backSideText = backSideText,
                    currentFlashCardId = card.id,
                    isRevealed = false,
                    cardBackgroundColorId = cardBackgroundColorId
                )
            }
        }
    }
}