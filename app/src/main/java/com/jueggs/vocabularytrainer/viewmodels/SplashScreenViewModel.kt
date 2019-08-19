package com.jueggs.vocabularytrainer.viewmodels

import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.SplashScreenViewState
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class SplashScreenViewModel(
    private val flashCardRepository: FlashCardRepository,
    private val flashCardBoxService: FlashCardBoxService
) : BaseViewModel<SplashScreenViewState>(SplashScreenViewState()) {
    fun checkSomethingToLearn() {
        launch {
            val now = DateTime.now()
            val isSomethingToLearn = FlashCardBox.values().any {
                flashCardRepository.readByBoxNumberAndExpiryDate(it.number, flashCardBoxService.getBoxExpiryDate(it, now)).any()
            }

            val navigationId = if (isSomethingToLearn) R.id.action_splashScreenFragment_to_learnFragment else R.id.action_splashScreenFragment_to_nothingToLearnFragment
            viewStateStore.dispatch(Trigger { copy(navigationId = navigationId) })
        }
    }
}