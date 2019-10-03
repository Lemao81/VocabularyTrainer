package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.Util
import com.jueggs.jutils.extension.join
import com.jueggs.jutils.usecase.MultipleViewStatesUseCase
import org.joda.time.DateTime

class ShowNextFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val flashCardBoxService: IFlashCardBoxService
) : MultipleViewStatesUseCase<LearnViewState>() {

    override suspend fun execute() {
        val now = DateTime.now()
        var nextCard: FlashCard? = null
        run loop@{
            FlashCardBox.values().forEach { box ->
                val dueFlashCards = flashCardRepository.readByBoxAndExpiryDate(box, flashCardBoxService.getBoxExpiryDate(box, now))
                if (dueFlashCards.any()) {
                    nextCard = dueFlashCards.minBy { it.lastLearnedDate }

                    return@loop
                }
            }
        }
        if (nextCard == null) {
            triggerViewState { copy(isShouldNavigateToNothingToLearn = true) }

            return
        }
        nextCard?.let { card ->
            val backSideText = if (card.backSideTexts.size == 1) {
                card.backSideTexts.first()
            } else {
                card.backSideTexts.mapIndexed { index, t -> "${index + 1}.  $t" }.join(Util.lineSeparator)
            }
            alterViewState {
                copy(
                    frontSideText = card.frontSideText,
                    backSideText = backSideText,
                    currentFlashCardId = card.id,
                    isRevealed = false,
                    nextShownFlashCardBox = card.box
                )
            }
        }
    }
}