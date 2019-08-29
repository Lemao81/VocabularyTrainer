package com.jueggs.vocabularytrainer.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.common.interfaces.StatsViewModel
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.DismissCorrectFlashCardData
import com.jueggs.vocabularytrainer.usecases.*
import com.jueggs.vocabularytrainer.viewstates.LearnViewState

class LearnViewModel(
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase,
    private val dismissWrongFlashCardUseCase: DismissWrongFlashCardUseCase,
    private val dismissCorrectFlashCardUseCase: DismissCorrectFlashCardUseCase,
    private val removeFlashCardUseCase: RemoveFlashCardUseCase,
    private val updateLearnViewStatsUseCase: UpdateLearnViewStatsUseCase,
    private val context: Context
) : BaseViewModel<LearnViewState>(LearnViewState()), StatsViewModel {
    override val stats: MutableList<MutableLiveData<String>> = mutableListOf()
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()
    var currentFlashCardId: Long? = null

    init {
        repeat(6) {
            stats.add(MutableLiveData())
        }
    }

    fun showNextFlashCard() = viewStateStore.dispatchChannel(showNextFlashCardUseCase())

    fun revealFlashCardBackSide() = viewStateStore.dispatch(Alter { copy(isRevealed = true) })

    fun dismissWrongFlashCard() = viewStateStore.dispatchChannel(dismissWrongFlashCardUseCase(currentFlashCardId), showNextFlashCardUseCase(), updateLearnViewStatsUseCase())

    fun dismissCorrectFlashCard() {
        val data = DismissCorrectFlashCardData(currentFlashCardId, context.getString(R.string.message_card_learned))
        viewStateStore.dispatchChannel(dismissCorrectFlashCardUseCase(data), showNextFlashCardUseCase(), updateLearnViewStatsUseCase())
    }

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_learnFragment_to_addFlashCardFragment) })

    fun removeFlashCard() = viewStateStore.dispatchChannel(removeFlashCardUseCase(currentFlashCardId), showNextFlashCardUseCase(), updateLearnViewStatsUseCase())

    fun updateStats() = viewStateStore.dispatchChannel(updateLearnViewStatsUseCase())
}