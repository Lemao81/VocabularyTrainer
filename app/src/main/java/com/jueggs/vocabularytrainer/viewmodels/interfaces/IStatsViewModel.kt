package com.jueggs.vocabularytrainer.viewmodels.interfaces

import com.jueggs.vocabularytrainer.models.StatsViewModelData

interface IStatsViewModel {
    val stats: MutableList<StatsViewModelData>
}