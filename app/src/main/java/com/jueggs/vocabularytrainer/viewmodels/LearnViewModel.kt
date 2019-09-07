package com.jueggs.vocabularytrainer.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.common.interfaces.IStatsViewModel
import com.jueggs.domain.models.DismissCorrectFlashCardData
import com.jueggs.domain.usecases.DismissCorrectFlashCardUseCase
import com.jueggs.domain.usecases.DismissWrongFlashCardUseCase
import com.jueggs.domain.usecases.RemoveFlashCardUseCase
import com.jueggs.domain.usecases.ShowNextFlashCardUseCase
import com.jueggs.domain.usecases.UpdateLearnViewStatsUseCase
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.Alter
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.vocabularytrainer.R

class LearnViewModel(
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase,
    private val dismissWrongFlashCardUseCase: DismissWrongFlashCardUseCase,
    private val dismissCorrectFlashCardUseCase: DismissCorrectFlashCardUseCase,
    private val removeFlashCardUseCase: RemoveFlashCardUseCase,
    private val updateLearnViewStatsUseCase: UpdateLearnViewStatsUseCase,
    private val context: Context
) : BaseViewModel<LearnViewState>(LearnViewState()), IStatsViewModel {
    override val stats: MutableList<MutableLiveData<String>> = mutableListOf()
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()
    val boxNumber = MutableLiveData<String>()
    var currentFlashCardId: Long? = null

    init {
        repeat(6) {
            stats.add(MutableLiveData())
        }
    }

    fun showNextFlashCard() = viewStateStore.dispatch(showNextFlashCardUseCase())

    fun revealFlashCardBackSide() = viewStateStore.dispatch(Alter { copy(isRevealed = true) })

    fun dismissWrongFlashCard() = viewStateStore.dispatch(dismissWrongFlashCardUseCase(currentFlashCardId), showNextFlashCardUseCase(), updateLearnViewStatsUseCase())

    fun dismissCorrectFlashCard() {
        val data = DismissCorrectFlashCardData(currentFlashCardId, context.getString(R.string.message_card_learned))
        viewStateStore.dispatch(dismissCorrectFlashCardUseCase(data), showNextFlashCardUseCase(), updateLearnViewStatsUseCase())
    }

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToAddFlashCard = true) })

    fun showRemoveFlashCardConfirmation() = viewStateStore.dispatch(Alter { copy(isShouldShowRemoveFlashCardConfirmation = true) })

    fun removeFlashCard() = viewStateStore.dispatch(removeFlashCardUseCase(currentFlashCardId), showNextFlashCardUseCase(), updateLearnViewStatsUseCase())

    fun editFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToEditFlashCard = true) })

    fun cancelFlashCardRemoval() = viewStateStore.dispatch(Alter { copy(isShouldShowRemoveFlashCardConfirmation = false) })

    fun updateStats() = viewStateStore.dispatch(updateLearnViewStatsUseCase())
}