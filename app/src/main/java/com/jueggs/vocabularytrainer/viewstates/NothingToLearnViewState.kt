package com.jueggs.vocabularytrainer.viewstates

data class NothingToLearnViewState(
    val navigationId: Int? = null,
    val isShouldCloseApp: Boolean = false,
    val stats1: Int = 0,
    val stats2: Int = 0,
    val stats3: Int = 0,
    val stats4: Int = 0,
    val stats5: Int = 0,
    val stats6: Int = 0
)