package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.Util
import com.jueggs.jutils.extension.join
import com.jueggs.jutils.extension.random
import com.jueggs.jutils.usecase.MultipleViewStatesUseCase
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val flashCardBoxService: IFlashCardBoxService
) : MultipleViewStatesUseCase<LearnViewState>() {

    override suspend fun execute() {
        val nextCard = findNextDueFlashCard()
        if (nextCard == null) {
            triggerViewState { copy(isShouldNavigateToNothingToLearn = true) }

            return
        }
        val backSideText = if (nextCard.backSideTexts.size == 1) {
            nextCard.backSideTexts.first()
        } else {
            nextCard.backSideTexts.mapIndexed { index, t -> "${index + 1}.  $t" }.join(Util.lineSeparator)
        }
        val flashCards = flashCardRepository.readAll()
        val groups = flashCards.groupBy { it.box }
        alterViewState {
            copy(
                frontSideText = nextCard.frontSideText,
                backSideText = backSideText,
                currentFlashCardId = nextCard.id,
                isRevealed = false,
                nextFlashCardBox = nextCard.box,
                stats1 = groups[FlashCardBox.ONE]?.size ?: 0,
                stats2 = groups[FlashCardBox.TWO]?.size ?: 0,
                stats3 = groups[FlashCardBox.THREE]?.size ?: 0,
                stats4 = groups[FlashCardBox.FOUR]?.size ?: 0,
                stats5 = groups[FlashCardBox.FIVE]?.size ?: 0,
                stats6 = groups[FlashCardBox.SIX]?.size ?: 0
            )
        }
        triggerViewState { copy(isShouldAnimateCardDisplay = true) }
    }

    private suspend fun findNextDueFlashCard(): FlashCard? {
        val now = DateTime.now()
        FlashCardBox.values().forEach { box ->
            val dueFlashCards = flashCardRepository.readByBoxAndExpiryDate(box, flashCardBoxService.getBoxExpiryDate(box, now))
            if (dueFlashCards.any()) {
                return dueFlashCards.random()
            }
        }

        return null
    }
}