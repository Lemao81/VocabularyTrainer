package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.viewstates.NothingToLearnViewState

class UpdateNothingToLearnViewStatsUseCase(
    private val flashCardRepository: FlashCardRepository
) : MultipleViewStatesUseCase<NothingToLearnViewState>() {
    override suspend fun execute() {
        val flashCards = flashCardRepository.readAll()
        val groups = flashCards.groupBy { it.box }

        alterViewState {
            copy(
                stats1 = groups[FlashCardBox.ONE]?.size ?: 0,
                stats2 = groups[FlashCardBox.TWO]?.size ?: 0,
                stats3 = groups[FlashCardBox.THREE]?.size ?: 0,
                stats4 = groups[FlashCardBox.FOUR]?.size ?: 0,
                stats5 = groups[FlashCardBox.FIVE]?.size ?: 0,
                stats6 = groups[FlashCardBox.SIX]?.size ?: 0
            )
        }
    }
}