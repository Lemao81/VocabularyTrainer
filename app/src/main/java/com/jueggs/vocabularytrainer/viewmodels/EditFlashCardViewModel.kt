package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.Constant
import com.jueggs.domain.models.EditFlashCardData
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.usecases.LoadFlashCardForEditingUseCase
import com.jueggs.domain.usecases.UpdateFlashCardUseCase
import com.jueggs.domain.viewstates.EditFlashCardViewState
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IFlashCardInputViewModel

class EditFlashCardViewModel(
    private val loadFlashCardForEditingUseCase: LoadFlashCardForEditingUseCase,
    private val updateFlashCardUseCase: UpdateFlashCardUseCase
) : BaseViewModel<EditFlashCardViewState>(EditFlashCardViewState()), IFlashCardInputViewModel {
    override val frontSideText = MutableLiveData<String>()
    override val backSideTexts = (0 until Constant.BACKSIDEINPUT_COUNT).map { MutableLiveData<String>() }
    private var flashCardInEditingId: Long? = null

    fun updateFlashCard() {
        val data = EditFlashCardData(flashCardInEditingId, getInputData())
        viewStateStore.dispatch(updateFlashCardUseCase(data))
    }

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })

    fun loadFlashCardForEditing(flashCardId: Long) {
        flashCardInEditingId = flashCardId
        viewStateStore.dispatch(loadFlashCardForEditingUseCase(flashCardId))
    }

    private fun getInputData() = FlashCardInputData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
}