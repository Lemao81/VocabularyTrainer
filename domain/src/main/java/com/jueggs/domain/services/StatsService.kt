package com.jueggs.domain.services

import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.models.StatsData
import com.jueggs.domain.services.interfaces.IFlashCardService
import com.jueggs.domain.services.interfaces.IStatsService
import org.joda.time.DateTime

class StatsService(
    private val flashCardService: IFlashCardService
) : IStatsService {
    override fun createStatsData(cards: List<FlashCard>?): StatsData {
        val cardCount = cards?.size ?: 0
        val now = DateTime.now()
        val dueCardCount = cards?.filter { flashCardService.isCardDueToLearn(it, now) }?.size ?: 0

        return StatsData(cardCount, dueCardCount > 0)
    }
}