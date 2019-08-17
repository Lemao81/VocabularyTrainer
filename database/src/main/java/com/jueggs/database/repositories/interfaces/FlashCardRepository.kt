package com.jueggs.database.repositories.interfaces

import com.jueggs.database.entities.FlashCardEntity

interface FlashCardRepository {
    fun readAll(): List<FlashCardEntity>

    fun readById(id: Long): FlashCardEntity

    fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>

    fun insert(card: FlashCardEntity)

    fun update(card: FlashCardEntity)

    fun delete(card: FlashCardEntity)

    fun deleteById(id: Long)
}