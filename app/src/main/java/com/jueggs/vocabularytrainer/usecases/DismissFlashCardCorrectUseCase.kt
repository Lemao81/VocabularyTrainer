package com.jueggs.vocabularytrainer.usecases

import android.content.Context
import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import org.joda.time.DateTime

class DismissFlashCardCorrectUseCase(
    private val flashCardRepository: FlashCardRepository,
    private val context: Context
) : ViewStateUseCaseWithParameter<LearnViewState, Long> {
    override suspend fun invoke(param: Long): StateEvent<LearnViewState> {
        val flashCard = flashCardRepository.readById(param)

        return if (flashCard.boxNumber == FlashCardBox.values().maxBy { it.number }?.number) {
            flashCardRepository.delete(flashCard)
            val message = String.format(context.getString(R.string.message_card_learned), flashCard.frontSideText)
            Trigger { copy(longMessage = message) }
        } else {
            flashCard.boxNumber++
            flashCard.lastLearnedDate = DateTime.now().millis
            flashCardRepository.update(flashCard)
            Trigger { copy() }
        }
    }
}