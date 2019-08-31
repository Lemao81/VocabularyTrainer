package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.common.interfaces.IStatsViewModel
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.usecases.UpdateNothingToLearnViewStatsUseCase
import com.jueggs.vocabularytrainer.viewstates.NothingToLearnViewState

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

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_nothingToLearnFragment_to_addFlashCardFragment) })

    fun updateStats() = viewStateStore.dispatchChannel(updateNothingToLearnViewStatsUseCase())
}