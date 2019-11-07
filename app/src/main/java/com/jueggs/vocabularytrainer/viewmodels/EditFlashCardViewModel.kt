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
import com.jueggs.vocabularytrainer.App
import com.jueggs.vocabularytrainer.viewmodels.interfaces.IFlashCardInputViewModel

class EditFlashCardViewModel(
    private val loadFlashCardForEditingUseCase: LoadFlashCardForEditingUseCase,
    private val updateFlashCardUseCase: UpdateFlashCardUseCase
) : BaseViewModel<EditFlashCardViewState>(EditFlashCardViewState(), App.isDev), IFlashCardInputViewModel {
    override val frontSideText = MutableLiveData<String>()
    override val backSideTexts = (0 until Constant.BACKSIDEINPUT_COUNT).map { MutableLiveData<String>() }
    private var flashCardId: Long? = null

    fun updateFlashCard() {
        val data = EditFlashCardData(flashCardId, getInputData())
        viewStateStore.dispatch(updateFlashCardUseCase(data))
    }

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })

    fun loadFlashCard(id: Long) {
        flashCardId = id
        viewStateStore.dispatch(loadFlashCardForEditingUseCase(id))
    }

    fun onBackPressed() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })

    private fun getInputData() = FlashCardInputData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
}