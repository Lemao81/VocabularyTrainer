package com.jueggs.domain.viewstates

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.models.StatsData

data class LearnViewState(
    val longMessage: String? = null,
    val frontSideText: String? = null,
    val backSideText: String? = null,
    val isRevealing: Boolean = false,
    val isRevealed: Boolean = false,
    val isShouldAnimateCardDisplay: Boolean = false,
    val isShouldAnimateCardFlip: Boolean = false,
    val isShouldAnimateDismissCorrect: Boolean = false,
    val isShouldAnimateDismissWrong: Boolean = false,
    val currentFlashCardId: Long? = null,
    val nextFlashCardBox: FlashCardBox? = null,
    val isShouldShowRemoveFlashCardConfirmation: Boolean = false,
    val isShouldNavigateToEditFlashCard: Boolean = false,
    val isShouldNavigateToNothingToLearn: Boolean = false,
    val isShouldNavigateToAddFlashCard: Boolean = false,
    val isShouldMessageCardRemoved: Boolean = false,
    val stats1: StatsData = StatsData(),
    val stats2: StatsData = StatsData(),
    val stats3: StatsData = StatsData(),
    val stats4: StatsData = StatsData(),
    val stats5: StatsData = StatsData(),
    val stats6: StatsData = StatsData()
)