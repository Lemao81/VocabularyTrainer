package com.jueggs.domain.viewstates

data class NothingToLearnViewState(
    val isShouldNavigateToAddFlashCard: Boolean = false,
    val isShouldCloseApp: Boolean = false,
    val stats1: Int = 0,
    val stats2: Int = 0,
    val stats3: Int = 0,
    val stats4: Int = 0,
    val stats5: Int = 0,
    val stats6: Int = 0
)