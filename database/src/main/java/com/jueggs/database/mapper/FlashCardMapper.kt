package com.jueggs.database.mapper

import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.models.FlashCard
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.mapper.interfaces.IFlashCardMapper
import org.joda.time.DateTime

class FlashCardMapper : IFlashCardMapper {
    override fun mapEntityToFlashCard(entity: FlashCardEntity): FlashCard = FlashCard(
        id = entity.id,
        frontSideText = entity.frontSideText,
        backSideTexts = entity.backSideTexts,
        lastLearnedDate = DateTime(entity.lastLearnedDate),
        box = FlashCardBox.values().single { it.number == entity.boxNumber }
    )

    override fun mapFlashCardToEntity(flashCard: FlashCard): FlashCardEntity {
        return FlashCardEntity(
            id = flashCard.id,
            frontSideText = flashCard.frontSideText,
            backSideTexts = flashCard.backSideTexts,
            lastLearnedDate = flashCard.lastLearnedDate.millis,
            boxNumber = flashCard.box.number
        )
    }
}