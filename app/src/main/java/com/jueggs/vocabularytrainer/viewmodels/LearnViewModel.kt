package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.vocabularytrainer.usecases.DismissFlashCardCorrectUseCase
import com.jueggs.vocabularytrainer.usecases.DismissFlashCardWrongUseCase
import com.jueggs.vocabularytrainer.usecases.ShowNextFlashCardUseCase
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer

class LearnViewModel(
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase,
    private val dismissFlashCardWrongUseCase: DismissFlashCardWrongUseCase,
    private val dismissFlashCardCorrectUseCase: DismissFlashCardCorrectUseCase
) : BaseViewModel<LearnViewState>(LearnViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()
    var currentFlashCardId: Long? = null

    @ImplicitReflectionSerializer
    fun showNextFlashCard() = viewStateStore.dispatchAction(showNextFlashCardUseCase::invoke)

    fun revealFlashCardBackSide() = viewStateStore.dispatchAction(Alter { copy(isRevealed = true) })

    @ImplicitReflectionSerializer
    fun dismissFlashCardWrong() {
        launch {
            currentFlashCardId?.let { dismissFlashCardWrongUseCase(it) }
            viewStateStore.dispatchAction(showNextFlashCardUseCase::invoke)
        }
    }

    @ImplicitReflectionSerializer
    fun dismissFlashCardCorrect() {
        launch {
            currentFlashCardId?.let { dismissFlashCardCorrectUseCase(it) }
            viewStateStore.dispatchAction(showNextFlashCardUseCase::invoke)
        }
    }
}