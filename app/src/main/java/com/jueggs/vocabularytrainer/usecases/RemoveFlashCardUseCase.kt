package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.serialization.ImplicitReflectionSerializer

class RemoveFlashCardUseCase(
    private val flashCardRepository: FlashCardRepository
) : ViewStateUseCaseWithParameter<LearnViewState, Long> {
    @ImplicitReflectionSerializer
    override suspend fun invoke(param: Long): StateEvent<LearnViewState> {
        flashCardRepository.deleteById(param)
        return Trigger { copy() }
    }
}