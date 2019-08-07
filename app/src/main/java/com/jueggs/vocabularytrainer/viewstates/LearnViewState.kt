package com.jueggs.vocabularytrainer.viewstates

data class LearnViewState(
    val navigationId: Int? = null,
    val frontSideText: String? = null,
    val backSideTexts: List<String>? = null,
    val isRevealed: Boolean = false
)