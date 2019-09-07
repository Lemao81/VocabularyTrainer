package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.common.interfaces.IStatsViewModel
import com.jueggs.domain.usecases.UpdateNothingToLearnViewStatsUseCase
import com.jueggs.domain.viewstates.NothingToLearnViewState
import com.jueggs.jutils.usecase.Trigger

class NothingToLearnViewModel(
    private val updateNothingToLearnViewStatsUseCase: UpdateNothingToLearnViewStatsUseCase
) : BaseViewModel<NothingToLearnViewState>(NothingToLearnViewState()), IStatsViewModel {
    override val stats: MutableList<MutableLiveData<String>> = mutableListOf()

    init {
        repeat(6) {
            stats.add(MutableLiveData())
        }
    }

    fun closeApp() = viewStateStore.dispatch(Trigger { copy(isShouldCloseApp = true) })

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToAddFlashCard = true) })

    fun updateStats() = viewStateStore.dispatch(updateNothingToLearnViewStatsUseCase())
}