package com.jueggs.vocabularytrainer.viewmodels

import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.usecases.UpdateNothingToLearnViewStatsUseCase
import com.jueggs.domain.viewstates.NothingToLearnViewState
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.vocabularytrainer.models.StatsViewModelData
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IAddFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IStatsViewModel

class NothingToLearnViewModel(
    private val updateNothingToLearnViewStatsUseCase: UpdateNothingToLearnViewStatsUseCase
) : BaseViewModel<NothingToLearnViewState>(NothingToLearnViewState()), IStatsViewModel, IAddFlashCardViewModel {
    override val stats: MutableList<StatsViewModelData> = mutableListOf()

    init {
        repeat(6) { stats.add(StatsViewModelData()) }
    }

    override fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToAddFlashCard = true) })

    fun closeApp() = viewStateStore.dispatch(Trigger { copy(isShouldCloseApp = true) })

    fun updateStats() = viewStateStore.dispatch(updateNothingToLearnViewStatsUseCase())
}