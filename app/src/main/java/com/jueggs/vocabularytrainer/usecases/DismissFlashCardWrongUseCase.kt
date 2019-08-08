package com.jueggs.vocabularytrainer.usecases

import com.jueggs.andutils.usecase.UseCaseWithParameter
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.database.storagelayers.FlashCardDao
import org.joda.time.DateTime

class DismissFlashCardWrongUseCase(
    private val flashCardDao: FlashCardDao
) : UseCaseWithParameter<Long> {
    override suspend fun invoke(param: Long) {
        val flashCard = flashCardDao.readById(param)
        flashCard.boxNumber = FlashCardBox.ONE.number
        flashCard.lastLearnedDate = DateTime.now().millis
        flashCardDao.update(flashCard)
    }
}