package com.jueggs.domain.services.interfaces

import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.models.StatsData

interface IStatsService {
    fun createStatsData(cards: List<FlashCard>?): StatsData
}