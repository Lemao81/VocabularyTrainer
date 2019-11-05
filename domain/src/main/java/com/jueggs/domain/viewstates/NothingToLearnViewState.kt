package com.jueggs.domain.viewstates

import com.jueggs.domain.models.StatsData

data class NothingToLearnViewState(
    val isShouldNavigateToAddFlashCard: Boolean = false,
    val isShouldCloseApp: Boolean = false,
    val stats1: StatsData = StatsData(),
    val stats2: StatsData = StatsData(),
    val stats3: StatsData = StatsData(),
    val stats4: StatsData = StatsData(),
    val stats5: StatsData = StatsData(),
    val stats6: StatsData = StatsData()
)