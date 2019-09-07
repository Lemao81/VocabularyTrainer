package com.jueggs.database.mapper.interfaces

import com.jueggs.domain.models.FlashCard
import com.jueggs.database.entities.FlashCardEntity

interface IFlashCardMapper {
    fun mapEntityToFlashCard(entity: FlashCardEntity): FlashCard
    fun mapFlashCardToEntity(flashCard: FlashCard): FlashCardEntity
}