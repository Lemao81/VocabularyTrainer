package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.jutils.extension.maxIndex
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.usecases.AddFlashCardUseCase
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import kotlinx.serialization.ImplicitReflectionSerializer

class AddFlashCardViewModel(
    private val addFlashCardUseCase: AddFlashCardUseCase
) : BaseViewModel<AddFlashCardViewState>(AddFlashCardViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideTexts = mutableListOf<MutableLiveData<String>>()

    init {
        repeat(5) {
            backSideTexts.add(MutableLiveData())
        }
    }

    @ImplicitReflectionSerializer
    fun addFlashCard() = viewStateStore.dispatch {
        val data = AddFlashCardData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
        addFlashCardUseCase(data)
    }

    fun showBackSideInput() = viewStateStore.dispatch(Alter { copy(backSideViewsShownUpToIndex = Math.min(backSideViewsShownUpToIndex + 1, backSideTexts.maxIndex)) })

    fun hideBackSideInput() = viewStateStore.dispatch(
        Alter { copy(backSideViewsShownUpToIndex = Math.max(backSideViewsShownUpToIndex - 1, 0)) },
        Trigger { copy(focusedInputIndex = backSideViewsShownUpToIndex) }
    )

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })
}