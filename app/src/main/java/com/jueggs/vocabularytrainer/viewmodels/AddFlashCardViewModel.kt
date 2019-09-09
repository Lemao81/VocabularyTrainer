package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.Constant
import com.jueggs.domain.models.AddFlashCardData
import com.jueggs.domain.models.EditFlashCardData
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.usecases.AddFlashCardUseCase
import com.jueggs.domain.usecases.LoadFlashCardForEditingUseCase
import com.jueggs.domain.usecases.UpdateFlashCardUseCase
import com.jueggs.domain.viewstates.AddFlashCardViewState
import com.jueggs.jutils.usecase.Trigger

class AddFlashCardViewModel(
    private val addFlashCardUseCase: AddFlashCardUseCase,
    private val loadFlashCardForEditingUseCase: LoadFlashCardForEditingUseCase,
    private val updateFlashCardUseCase: UpdateFlashCardUseCase
) : BaseViewModel<AddFlashCardViewState>(AddFlashCardViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideTexts = mutableListOf<MutableLiveData<String>>()
    val isKeepAdding = MutableLiveData<Boolean>()
    private var flashCardInEditingId: Long? = null

    init {
        repeat(Constant.BACKSIDEINPUT_COUNT) {
            backSideTexts.add(MutableLiveData())
        }
    }

    fun focusFrontSideEdit() = viewStateStore.dispatch(Trigger { copy(isShouldFocusFrontSideEdit = true) })

    fun addFlashCard() {
        val data = AddFlashCardData(isKeepAdding.value, getInputData())
        viewStateStore.dispatch(addFlashCardUseCase(data))
    }

    fun updateFlashCard() {
        val data = EditFlashCardData(flashCardInEditingId, getInputData())
        viewStateStore.dispatch(updateFlashCardUseCase(data))
    }

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })

    fun loadFlashCardForEditing(flashCardId: Long) {
        flashCardInEditingId = flashCardId
        viewStateStore.dispatch(loadFlashCardForEditingUseCase(flashCardId))
    }

    private fun getInputData(): FlashCardInputData = FlashCardInputData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
}