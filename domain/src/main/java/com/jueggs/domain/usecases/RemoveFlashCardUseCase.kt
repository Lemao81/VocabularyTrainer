package com.jueggs.domain.usecases

import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCaseWithParameter

class RemoveFlashCardUseCase(
    private val flashCardRepository: IFlashCardRepository
) : MultipleViewStatesUseCaseWithParameter<LearnViewState, Long?>() {

    override suspend fun execute(param: Long?) {
        param?.let {
            flashCardRepository.deleteById(it)
            alterViewState { copy(isShouldShowRemoveFlashCardConfirmation = false) }
            triggerViewState { copy(isShouldMessageCardRemoved = true) }
        }
    }
}