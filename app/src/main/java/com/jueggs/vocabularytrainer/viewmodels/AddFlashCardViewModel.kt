package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.usecases.AddFlashCardUseCase
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import com.log4k.v
import kotlinx.serialization.ImplicitReflectionSerializer

class AddFlashCardViewModel(
    private val addFlashCardUseCase: AddFlashCardUseCase
) : BaseViewModel<AddFlashCardViewState>(AddFlashCardViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()

    @ImplicitReflectionSerializer
    fun addFlashCard() = viewStateStore.dispatchAction {
        val data = AddFlashCardData(frontSideText.value ?: "", listOf(backSideText.value ?: ""))
        addFlashCardUseCase(data)
    }

    fun cancel() = viewStateStore.dispatchAction(Trigger { copy(isShouldPopFragment = true) })
}