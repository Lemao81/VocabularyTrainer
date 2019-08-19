package com.jueggs.vocabularytrainer.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.jutils.extension.join
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import org.joda.time.DateTime

class LearnViewModel(
    private val flashCardRepository: FlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService,
    private val json: Json,
    private val context: Context
) : BaseViewModel<LearnViewState>(LearnViewState()) {
    val frontSideText = MutableLiveData<String>()
    val backSideText = MutableLiveData<String>()
    var currentFlashCardId: Long? = null

    @ImplicitReflectionSerializer
    fun showNextFlashCard() {
        launch {
            showNextFlashCardInternal()
        }
    }

    fun revealFlashCardBackSide() = viewStateStore.dispatch(Alter { copy(isRevealed = true) })

    @ImplicitReflectionSerializer
    fun dismissFlashCardWrong() {
        launch {
            currentFlashCardId?.let {
                val flashCard = flashCardRepository.readById(it)
                flashCard.boxNumber = FlashCardBox.ONE.number
                flashCard.lastLearnedDate = DateTime.now().millis
                flashCardRepository.update(flashCard)
            }
            showNextFlashCardInternal()
        }
    }

    @ImplicitReflectionSerializer
    fun dismissFlashCardCorrect() {
        launch {
            currentFlashCardId?.let {
                val flashCard = flashCardRepository.readById(it)
                if (flashCard.boxNumber == FlashCardBox.values().maxBy { it.number }?.number) {
                    flashCardRepository.delete(flashCard)
                    val message = String.format(context.getString(R.string.message_card_learned), flashCard.frontSideText)
                    viewStateStore.dispatch(Trigger { copy(longMessage = message) })
                } else {
                    flashCard.boxNumber++
                    flashCard.lastLearnedDate = DateTime.now().millis
                    flashCardRepository.update(flashCard)
                }
            }
            showNextFlashCardInternal()
        }
    }

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_learnFragment_to_addFlashCardFragment) })

    @ImplicitReflectionSerializer
    fun removeFlashCard() {
        currentFlashCardId?.let { id ->
            launch {
                flashCardRepository.deleteById(id)
                viewStateStore.dispatch(Trigger { copy(shortMessageId = R.string.message_card_removed) })
                showNextFlashCardInternal()
            }
        }
    }

    @ImplicitReflectionSerializer
    private suspend fun showNextFlashCardInternal() {
        val now = DateTime.now()
        var nextCard: FlashCardEntity? = null
        FlashCardBox.values().forEach { it ->
            val flashCards = flashCardRepository.readByBoxNumberAndExpiryDate(it.number, flashCardBoxService.getBoxExpiryDate(it, now))
            if (flashCards.any()) {
                nextCard = flashCards.sortedBy { it.lastLearnedDate }.first()

                return@forEach
            }
        }

        if (nextCard != null) {
            currentFlashCardId = nextCard?.id
            val backSideTexts = if (nextCard?.backSideTexts != null) json.parseList(nextCard?.backSideTexts!!) else emptyList<String>()

            viewStateStore.dispatch(Alter {
                copy(
                    frontSideText = nextCard?.frontSideText,
                    backSideText = backSideTexts.mapIndexed { index, t -> "${index + 1}.  $t" }.join(com.jueggs.jutils.Util.lineSeparator),
                    isRevealed = false
                )
            })
        } else {
            viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_learnFragment_to_nothingToLearnFragment) })
        }
    }
}