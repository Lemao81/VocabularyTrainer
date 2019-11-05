package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.services.interfaces.IStatsService
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.Util
import com.jueggs.jutils.extension.join
import com.jueggs.jutils.extension.random
import com.jueggs.jutils.usecase.MultipleViewStatesUseCase
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val flashCardBoxService: IFlashCardBoxService,
    private val statsService: IStatsService
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
                stats1 = statsService.createStatsData(groups[FlashCardBox.ONE]),
                stats2 = statsService.createStatsData(groups[FlashCardBox.TWO]),
                stats3 = statsService.createStatsData(groups[FlashCardBox.THREE]),
                stats4 = statsService.createStatsData(groups[FlashCardBox.FOUR]),
                stats5 = statsService.createStatsData(groups[FlashCardBox.FIVE]),
                stats6 = statsService.createStatsData(groups[FlashCardBox.SIX])
            )
        }
        triggerViewState { copy(isShouldAnimateCardDisplay = true) }
    }

    private suspend fun findNextDueFlashCard(): FlashCard? {
        val now = DateTime.now()
        FlashCardBox.values().forEach { box ->
            val dueFlashCards = flashCardRepository.readByBoxAndExpiryDate(box, flashCardBoxService.getBoxExpiryDate(box, now).millis)
            if (dueFlashCards.any()) {
                return dueFlashCards.random()
            }
        }

        return null
    }
}