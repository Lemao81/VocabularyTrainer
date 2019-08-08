package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.vocabularytrainer.usecases.ShowNextFlashCardUseCase
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.serialization.ImplicitReflectionSerializer

class LearnViewModel(
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase
) : BaseViewModel<LearnViewState>(LearnViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()

    @ImplicitReflectionSerializer
    fun showNextFlashCard() = viewStateStore.dispatchAction(showNextFlashCardUseCase::invoke)
}