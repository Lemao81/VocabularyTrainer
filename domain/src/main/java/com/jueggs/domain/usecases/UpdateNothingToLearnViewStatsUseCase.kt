package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.services.interfaces.IStatsService
import com.jueggs.domain.viewstates.NothingToLearnViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCase

class UpdateNothingToLearnViewStatsUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val statsService: IStatsService
) : MultipleViewStatesUseCase<NothingToLearnViewState>() {

    override suspend fun execute() {
        val flashCards = flashCardRepository.readAll()
        val groups = flashCards.groupBy { it.box }
        alterViewState {
            copy(
                stats1 = statsService.createStatsData(groups[FlashCardBox.ONE]),
                stats2 = statsService.createStatsData(groups[FlashCardBox.TWO]),
                stats3 = statsService.createStatsData(groups[FlashCardBox.THREE]),
                stats4 = statsService.createStatsData(groups[FlashCardBox.FOUR]),
                stats5 = statsService.createStatsData(groups[FlashCardBox.FIVE]),
                stats6 = statsService.createStatsData(groups[FlashCardBox.SIX])
            )
        }
    }
}