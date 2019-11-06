package com.jueggs.vocabularytrainer.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.models.DismissCorrectFlashCardData
import com.jueggs.domain.usecases.DismissCorrectFlashCardUseCase
import com.jueggs.domain.usecases.DismissWrongFlashCardUseCase
import com.jueggs.domain.usecases.RemoveFlashCardUseCase
import com.jueggs.domain.usecases.ShowNextFlashCardUseCase
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.Alter
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.vocabularytrainer.App
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.StatsViewModelData
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IAddFlashCardViewModel
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IStatsViewModel

class LearnViewModel(
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase,
    private val dismissCorrectFlashCardUseCase: DismissCorrectFlashCardUseCase,
    private val dismissWrongFlashCardUseCase: DismissWrongFlashCardUseCase,
    private val removeFlashCardUseCase: RemoveFlashCardUseCase,
    private val context: Context
) : BaseViewModel<LearnViewState>(LearnViewState(), App.isDev), IStatsViewModel, IAddFlashCardViewModel {
    override val stats: MutableList<StatsViewModelData> = mutableListOf()
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()
    val boxNumber = MutableLiveData<String>()
    var currentFlashCardId: Long? = null

    init {
        repeat(6) { stats.add(StatsViewModelData()) }
        viewStateStore.dispatch(showNextFlashCardUseCase())
    }

    override fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToAddFlashCard = true) })

    fun flipFlashCard() = viewStateStore.dispatch(Alter { copy(isRevealing = true) }, Trigger { copy(isShouldAnimateCardFlip = true) })

    fun setBackSideRevealed() = viewStateStore.dispatch(Alter { copy(isRevealed = true, isRevealing = false) })

    fun dismissCorrectFlashCard() {
        val data = DismissCorrectFlashCardData(currentFlashCardId, context.getString(R.string.message_card_learned))
        viewStateStore.dispatch(dismissCorrectFlashCardUseCase(data))
    }

    fun dismissWrongFlashCard() = viewStateStore.dispatch(dismissWrongFlashCardUseCase(currentFlashCardId))

    fun showNextFlashCard() = viewStateStore.dispatch(showNextFlashCardUseCase())

    fun showRemoveFlashCardConfirmation() = viewStateStore.dispatch(Alter { copy(isShouldShowRemoveFlashCardConfirmation = true) })

    fun removeFlashCard() = viewStateStore.dispatch(removeFlashCardUseCase(currentFlashCardId), showNextFlashCardUseCase())

    fun editFlashCard() = viewStateStore.dispatch(Trigger { copy(isShouldNavigateToEditFlashCard = true) })

    fun cancelFlashCardRemoval() = viewStateStore.dispatch(Alter { copy(isShouldShowRemoveFlashCardConfirmation = false) })
}