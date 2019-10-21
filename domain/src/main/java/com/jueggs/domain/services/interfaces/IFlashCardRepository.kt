package com.jueggs.domain.services.interfaces

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.models.FlashCard

interface IFlashCardRepository {
    suspend fun readAll(): List<FlashCard>
    suspend fun readById(id: Long): FlashCard
    suspend fun readByBoxAndExpiryDate(box: FlashCardBox, expiryDate: Long): List<FlashCard>
    suspend fun insert(flashCard: FlashCard)
    suspend fun insertAll(flashCards: List<FlashCard>)
    suspend fun update(flashCard: FlashCard)
    suspend fun delete(flashCard: FlashCard)
    suspend fun deleteById(id: Long)
}