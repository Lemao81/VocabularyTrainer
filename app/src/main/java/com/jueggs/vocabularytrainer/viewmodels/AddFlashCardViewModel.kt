package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.result.Invalid
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.jutils.extension.maxIndex
import com.jueggs.vocabularytrainer.models.AddFlashCardData
import com.jueggs.vocabularytrainer.validators.AddFlashCardInputValidator
import com.jueggs.vocabularytrainer.viewstates.AddFlashCardViewState
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import org.joda.time.DateTime

class AddFlashCardViewModel(
    private val flashCardRepository: FlashCardRepository,
    private val json: Json,
    private val addFlashCardInputValidator: AddFlashCardInputValidator
) : BaseViewModel<AddFlashCardViewState>(AddFlashCardViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideTexts = mutableListOf<MutableLiveData<String>>()

    init {
        repeat(5) {
            backSideTexts.add(MutableLiveData())
        }
    }

    @ImplicitReflectionSerializer
    fun addFlashCard() {
        launch {
            val data = AddFlashCardData(frontSideText.value.orEmpty(), backSideTexts.map { it.value.orEmpty() })
            when (val validationResult = addFlashCardInputValidator(data)) {
                is Invalid -> viewStateStore.dispatch(Trigger { copy(longMessageId = validationResult.resId) })
                else -> {
                    val newFlashCard = FlashCardEntity(
                        id = null,
                        frontSideText = data.frontSideText,
                        backSideTexts = json.stringify(data.backSideTexts.filterNot { it.isBlank() }),
                        boxNumber = FlashCardBox.ONE.number,
                        lastLearnedDate = DateTime.now().millis
                    )
                    flashCardRepository.insert(newFlashCard)
                    frontSideText.postValue("")
                    backSideTexts.forEach { it.postValue("") }

                    viewStateStore.dispatch(Trigger {
                        copy(
                            shortMessageId = com.jueggs.vocabularytrainer.R.string.message_card_added,
                            focusedInputIndex = com.jueggs.jutils.INVALID,
                            backSideViewsShownUpToIndex = 0
                        )
                    })
                }
            }
        }
    }

    fun showBackSideInput() = viewStateStore.dispatch(
        Alter { copy(backSideViewsShownUpToIndex = Math.min(backSideViewsShownUpToIndex + 1, backSideTexts.maxIndex)) },
        Trigger { copy(focusedInputIndex = backSideViewsShownUpToIndex) }
    )

    fun hideBackSideInput() = viewStateStore.dispatch(
        Alter { copy(backSideViewsShownUpToIndex = Math.max(backSideViewsShownUpToIndex - 1, 0)) },
        Trigger { copy(focusedInputIndex = backSideViewsShownUpToIndex) }
    )

    fun cancel() = viewStateStore.dispatch(Trigger { copy(isShouldPopFragment = true) })
}