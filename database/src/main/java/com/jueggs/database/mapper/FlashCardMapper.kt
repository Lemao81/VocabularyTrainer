package com.jueggs.database.mapper

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.interfaces.ISerializer
import com.jueggs.domain.models.FlashCard
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.mapper.interfaces.IFlashCardMapper
import org.joda.time.DateTime

class FlashCardMapper(
    private val serializer: ISerializer
) : IFlashCardMapper {
    override fun mapEntityToFlashCard(entity: FlashCardEntity): FlashCard = FlashCard(
        id = entity.id,
        frontSideText = entity.frontSideText,
        backSideTexts = serializer.parseList(entity.backSideTexts, String::class),
        lastLearnedDate = DateTime(entity.lastLearnedDate),
        box = FlashCardBox.values().single { it.number == entity.boxNumber }
    )

    override fun mapFlashCardToEntity(flashCard: FlashCard): FlashCardEntity {
        return FlashCardEntity(
            id = flashCard.id,
            frontSideText = flashCard.frontSideText,
            backSideTexts = serializer.stringify(flashCard.backSideTexts.filterNot { it.isBlank() }, String::class),
            lastLearnedDate = flashCard.lastLearnedDate.millis,
            boxNumber = flashCard.box.number
        )
    }
}