package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCase
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.viewstates.NothingToLearnViewState

class UpdateNothingToLearnViewStatsUseCase(
    private val flashCardRepository: FlashCardRepository
) : MultipleViewStatesUseCase<NothingToLearnViewState>() {
    override suspend fun execute() {
        val flashCards = flashCardRepository.readAll()
        val groups = flashCards.groupBy { it.boxNumber }

        alterViewState {
            copy(
                stats1 = groups[com.jueggs.common.enums.FlashCardBox.ONE.number]?.size ?: 0,
                stats2 = groups[com.jueggs.common.enums.FlashCardBox.TWO.number]?.size ?: 0,
                stats3 = groups[com.jueggs.common.enums.FlashCardBox.THREE.number]?.size ?: 0,
                stats4 = groups[com.jueggs.common.enums.FlashCardBox.FOUR.number]?.size ?: 0,
                stats5 = groups[com.jueggs.common.enums.FlashCardBox.FIVE.number]?.size ?: 0,
                stats6 = groups[com.jueggs.common.enums.FlashCardBox.SIX.number]?.size ?: 0
            )
        }
    }
}