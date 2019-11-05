package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.domain.viewstates.SplashScreenViewState
import com.jueggs.jutils.usecase.MultipleViewStatesUseCase
import org.joda.time.DateTime

class CheckSomethingToLearnUseCase(
    private val flashCardRepository: IFlashCardRepository,
    private val flashCardBoxService: IFlashCardBoxService
) : MultipleViewStatesUseCase<SplashScreenViewState>() {

    override suspend fun execute() {
        val now = DateTime.now()
        val isSomethingToLearn = FlashCardBox.values().any {
            flashCardRepository.readByBoxAndExpiryDate(it, flashCardBoxService.getBoxExpiryDate(it, now).millis).any()
        }
        triggerViewState {
            copy(
                isShouldNavigateToLearnFragment = isSomethingToLearn,
                isShouldNavigateToNothingToLearnFragment = !isSomethingToLearn
            )
        }
    }
}