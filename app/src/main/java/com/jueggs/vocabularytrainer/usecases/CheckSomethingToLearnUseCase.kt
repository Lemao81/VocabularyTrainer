package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.aac.StateEvent
import com.jueggs.andutils.aac.Trigger
import com.jueggs.andutils.usecase.UseCase
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.services.FlashCardBoxService
import com.jueggs.database.storagelayers.FlashCardDao
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.SplashScreenViewState
import org.joda.time.DateTime

class CheckSomethingToLearnUseCase(
    private val flashCardDao: FlashCardDao,
    private val flashCardBoxService: FlashCardBoxService
) : UseCase<SplashScreenViewState> {
    override suspend fun invoke(): StateEvent<SplashScreenViewState> {
        val now = DateTime()
        val isSomethingToLearn = FlashCardBox.values().any {
            flashCardDao.readByBoxNumberAndExpiryDate(it.id, flashCardBoxService.getBoxExpiryDate(it, now)).any()
        }

        return if (isSomethingToLearn) {
            Trigger { copy(navigationId = R.id.action_splashScreenFragment_to_learnFragment) }
        } else {
            Trigger { copy(navigationId = R.id.action_splashScreenFragment_to_nothingToLearnFragment) }
        }
    }
}