package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCase

class UpdateLearnViewStatsUseCase(
    private val flashCardRepository: IFlashCardRepository
) : MultipleViewStatesUseCase<LearnViewState>() {

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