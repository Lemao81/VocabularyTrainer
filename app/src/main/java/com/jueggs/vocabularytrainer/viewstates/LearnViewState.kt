package com.jueggs.vocabularytrainer.viewstates

data class LearnViewState(
    val navigationId: Int? = null,
    val shortMessageId: Int? = null,
    val longMessage: String? = null,
    val frontSideText: String? = null,
    val backSideText: String? = null,
    val isRevealed: Boolean = false,
    val currentFlashCardId: Long? = null,
    val boxNumber: Int? = null,
    val cardBackgroundColorId: Int? = null,
    val isShouldShowRemoveFlashCardConfirmation: Boolean = false,
    val stats1: Int = 0,
    val stats2: Int = 0,
    val stats3: Int = 0,
    val stats4: Int = 0,
    val stats5: Int = 0,
    val stats6: Int = 0
)