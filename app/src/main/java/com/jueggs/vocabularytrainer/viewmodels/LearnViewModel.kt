package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.usecases.DismissFlashCardCorrectUseCase
import com.jueggs.vocabularytrainer.usecases.DismissFlashCardWrongUseCase
import com.jueggs.vocabularytrainer.usecases.RemoveFlashCardUseCase
import com.jueggs.vocabularytrainer.usecases.ShowNextFlashCardUseCase
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer

class LearnViewModel(
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase,
    private val dismissFlashCardWrongUseCase: DismissFlashCardWrongUseCase,
    private val dismissFlashCardCorrectUseCase: DismissFlashCardCorrectUseCase,
    private val removeFlashCardUseCase: RemoveFlashCardUseCase
) : BaseViewModel<LearnViewState>(LearnViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()
    var currentFlashCardId: Long? = null

    @ImplicitReflectionSerializer
    fun showNextFlashCard() {
        launch {
            viewStateStore.dispatch(showNextFlashCardUseCase(this@LearnViewModel))
        }
    }

    fun revealFlashCardBackSide() = viewStateStore.dispatch(Alter { copy(isRevealed = true) })

    @ImplicitReflectionSerializer
    fun dismissFlashCardWrong() {
        launch {
            currentFlashCardId?.let { dismissFlashCardWrongUseCase(it) }
            viewStateStore.dispatch(showNextFlashCardUseCase(this@LearnViewModel))
        }
    }

    @ImplicitReflectionSerializer
    fun dismissFlashCardCorrect() {
        launch {
            currentFlashCardId?.let { dismissFlashCardCorrectUseCase(it) }
            viewStateStore.dispatch(showNextFlashCardUseCase(this@LearnViewModel))
        }
    }

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_learnFragment_to_addFlashCardFragment) })

    @ImplicitReflectionSerializer
    fun removeFlashCard() {
        currentFlashCardId?.let { id ->
            launch {
                viewStateStore.dispatch(removeFlashCardUseCase(id), showNextFlashCardUseCase(this@LearnViewModel))
            }
        }
    }
}