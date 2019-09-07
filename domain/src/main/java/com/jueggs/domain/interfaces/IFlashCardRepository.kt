package com.jueggs.domain.interfaces

import com.jueggs.domain.models.FlashCard

interface IFlashCardRepository {
    suspend fun readAll(): List<FlashCard>
    suspend fun readById(id: Long): FlashCard
    suspend fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCard>
    suspend fun insert(flashCard: FlashCard)
    suspend fun update(flashCard: FlashCard)
    suspend fun delete(flashCard: FlashCard)
    suspend fun deleteById(id: Long)
}