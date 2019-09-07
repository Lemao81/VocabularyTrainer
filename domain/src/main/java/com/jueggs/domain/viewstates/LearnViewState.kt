package com.jueggs.domain.viewstates

import com.jueggs.domain.enums.FlashCardBox

data class LearnViewState(
    val longMessage: String? = null,
    val frontSideText: String? = null,
    val backSideText: String? = null,
    val isRevealed: Boolean = false,
    val currentFlashCardId: Long? = null,
    val nextShownFlashCardBox: FlashCardBox? = null,
    val isShouldShowRemoveFlashCardConfirmation: Boolean = false,
    val isShouldNavigateToEditFlashCard: Boolean = false,
    val isShouldNavigateToNothingToLearn: Boolean = false,
    val isShouldNavigateToAddFlashCard: Boolean = false,
    val isShouldMessageCardRemoved: Boolean = false,
    val stats1: Int = 0,
    val stats2: Int = 0,
    val stats3: Int = 0,
    val stats4: Int = 0,
    val stats5: Int = 0,
    val stats6: Int = 0
)