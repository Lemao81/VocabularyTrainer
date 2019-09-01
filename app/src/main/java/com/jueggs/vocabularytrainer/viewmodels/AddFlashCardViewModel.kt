package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.jutils.extension.maxIndex
import com.jueggs.vocabularytrainer.models.EditFlashCardData
import com.jueggs.vocabularytrainer.models.FlashCardInputData
import com.jueggs.vocabularytrainer.usecases.AddFlashCardUseCase
import com.jueggs.vocabularytrainer.usecases.LoadFlashCardForEditingUseCase
import com.jueggs.vocabularytrainer.usecases.UpdateFlashCardUseCase
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState

class AddFlashCardViewModel(
    private val addFlashCardUseCase: AddFlashCardUseCase,
    private val loadFlashCardForEditingUseCase: LoadFlashCardForEditingUseCase,
    private val updateFlashCardUseCase: UpdateFlashCardUseCase
) : BaseViewModel<AddFlashCardViewState>(AddFlashCardViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideTexts = mutableListOf<MutableLiveData<String>>()
    private var flashCardInEditingId: Long? = null

    init {
        repeat(5) {
            backSideTexts.add(MutableLiveData())
        }
    }

    fun focusFrontSideEdit() = viewStateStore.dispatch(Trigger { copy(isShouldFocusFrontSideEdit = true) })

    fun addFlashCard() = viewStateStore.dispatch(addFlashCardUseCase(getInputData()))

    fun updateFlashCard() {
        val data = EditFlashCardData(flashCardInEditingId, getInputData())
        viewStateStore.dispatch(updateFlashCardUseCase(data))
    }

    fun showBackSideInput() = viewStateStore.dispatch(
        Alter { copy(backSideViewsShownUpToIndex = Math.min(backSideViewsShownUpToIndex + 1, backSideTexts.maxIndex)) },
        Trigger { copy(focusedInputIndex = backSideViewsShownUpToIndex) }
    )

    fun hideBackSideInput() = viewStateStore.dispatch(
        Trigger { copy(focusedInputIndex = Math.max(backSideViewsShownUpToIndex - 1, 0)) },
        Alter { copy(backSideViewsShownUpToIndex = Math.max(backSideViewsShownUpToIndex - 1, 0)) })

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })

    fun loadFlashCardForEditing(flashCardId: Long) {
        flashCardInEditingId = flashCardId
        viewStateStore.dispatch(loadFlashCardForEditingUseCase(flashCardId))
    }

    private fun getInputData(): FlashCardInputData = FlashCardInputData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
}