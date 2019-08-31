package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.MultipleViewStatesUseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.common.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.SplashScreenViewState
import org.joda.time.DateTime

class CheckSomethingToLearnUseCase(
    private val flashCardRepository: FlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService
) : MultipleViewStatesUseCase<SplashScreenViewState>() {

    override suspend fun execute() {
        val now = DateTime.now()
        val isSomethingToLearn = FlashCardBox.values().any {
            flashCardRepository.readByBoxNumberAndExpiryDate(it.number, flashCardBoxService.getBoxExpiryDate(it, now)).any()
        }
        val navigationId = if (isSomethingToLearn) {
            R.id.action_splashScreenFragment_to_learnFragment
        } else {
            R.id.action_splashScreenFragment_to_nothingToLearnFragment
        }
        triggerViewState { copy(navigationId = navigationId) }
    }
}