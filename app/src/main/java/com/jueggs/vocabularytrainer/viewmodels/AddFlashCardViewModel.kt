package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.Constant
import com.jueggs.domain.models.AddFlashCardData
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.usecases.AddFlashCardUseCase
import com.jueggs.domain.viewstates.AddFlashCardViewState
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IFlashCardInputViewModel

class AddFlashCardViewModel(
    private val addFlashCardUseCase: AddFlashCardUseCase
) : BaseViewModel<AddFlashCardViewState>(AddFlashCardViewState()), IFlashCardInputViewModel {
    override val frontSideText = MutableLiveData<String>()
    override val backSideTexts = (0 until Constant.BACKSIDEINPUT_COUNT).map { MutableLiveData<String>() }
    val isKeepAdding = MutableLiveData<Boolean>()

    fun focusFrontSideEdit() = viewStateStore.dispatch(Trigger { copy(isShouldFocusFrontSideEdit = true) })

    fun addFlashCard() {
        val data = AddFlashCardData(isKeepAdding.value, getInputData())
        viewStateStore.dispatch(addFlashCardUseCase(data))
    }

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })

    private fun getInputData() = FlashCardInputData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
}