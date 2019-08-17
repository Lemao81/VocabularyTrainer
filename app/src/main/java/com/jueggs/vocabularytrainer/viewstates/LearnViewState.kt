package com.jueggs.vocabularytrainer.viewstates

data class LearnViewState(
    val navigationId: Int? = null,
    val shortMessageId: Int? = null,
    val longMessage: String? = null,
    val frontSideText: String? = null,
    val backSideText: String? = null,
    val isRevealed: Boolean = false
)