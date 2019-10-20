package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.usecases.UpdateNothingToLearnViewStatsUseCase
import com.jueggs.domain.viewstates.NothingToLearnViewState
import com.jueggs.jutils.usecase.Trigger

class NothingToLearnViewModel(
    private val updateNothingToLearnViewStatsUseCase: UpdateNothingToLearnViewStatsUseCase
) : BaseViewModel<NothingToLearnViewState>(NothingToLearnViewState()), IStatsViewModel, IAddFlashCardViewModel {
    override val stats: MutableList<MutableLiveData<String>> = mutableListOf()

    init {
        repeat(6) {
            stats.add(MutableLiveData())
        }
    }

    override fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToAddFlashCard = true) })

    fun closeApp() = viewStateStore.dispatch(Trigger { copy(isShouldCloseApp = true) })

    fun updateStats() = viewStateStore.dispatch(updateNothingToLearnViewStatsUseCase())
}