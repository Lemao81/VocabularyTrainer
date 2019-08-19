package com.jueggs.database.repositories.interfaces

import com.jueggs.database.entities.FlashCardEntity

interface FlashCardRepository {
    suspend fun readAll(): List<FlashCardEntity>

    suspend fun readById(id: Long): FlashCardEntity

    suspend fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>

    suspend fun insert(card: FlashCardEntity)

    suspend fun update(card: FlashCardEntity)

    suspend fun delete(card: FlashCardEntity)

    suspend fun deleteById(id: Long)
}