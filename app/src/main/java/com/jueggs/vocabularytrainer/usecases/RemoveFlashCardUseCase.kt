package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.usecase.ViewStateUseCaseWithParameter
import com.jueggs.database.storagelayers.FlashCardDao
import com.jueggs.vocabularytrainer.viewstates.LearnViewState
import kotlinx.serialization.ImplicitReflectionSerializer

class RemoveFlashCardUseCase(
    private val flashCardDao: FlashCardDao,
    private val showNextFlashCardUseCase: ShowNextFlashCardUseCase
) : ViewStateUseCaseWithParameter<LearnViewState, Long> {
    @ImplicitReflectionSerializer
    override suspend fun invoke(param: Long): StateEvent<LearnViewState> {
        flashCardDao.deleteById(param)
        return showNextFlashCardUseCase()
    }
}