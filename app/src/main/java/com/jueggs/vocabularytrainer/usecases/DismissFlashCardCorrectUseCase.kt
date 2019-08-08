package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.UseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.storagelayers.FlashCardDao
import org.joda.time.DateTime

class DismissFlashCardCorrectUseCase(
    private val flashCardDao: FlashCardDao
) : UseCaseWithParameter<Long> {
    override suspend fun invoke(param: Long) {
        val flashCard = flashCardDao.readById(param)
        if (flashCard.boxNumber == FlashCardBox.values().maxBy { it.number }?.number) {
            flashCardDao.delete(flashCard)
        } else {
            flashCard.boxNumber++
            flashCard.lastLearnedDate = DateTime.now().millis
            flashCardDao.update(flashCard)
        }
    }
}