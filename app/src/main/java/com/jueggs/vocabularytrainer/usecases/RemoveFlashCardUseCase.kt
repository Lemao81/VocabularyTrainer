package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCaseWithParameter
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.LearnViewState

class RemoveFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<LearnViewState, Long?>() {

    override suspend fun execute(param: Long?) {
        param?.let {
            flashCardRepository.deleteById(it)
            triggerViewState { copy(shortMessageId = R.string.message_card_removed) }
        }
    }
}