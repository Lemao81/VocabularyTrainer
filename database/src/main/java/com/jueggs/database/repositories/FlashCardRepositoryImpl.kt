package com.jueggs.database.repositories

import com.jueggs.database.daos.FlashCardDao
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import org.joda.time.DateTime

class FlashCardRepositoryImpl(
    private val flashCardDao: FlashCardDao
) : FlashCardRepository {
    override suspend fun readAll() = flashCardDao.readAll()

    override suspend fun readById(id: Long) = flashCardDao.readById(id)

    override suspend fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long) = flashCardDao.readByBoxNumberAndExpiryDate(boxNumber, expiryDate)

    override suspend fun insert(card: FlashCardEntity) {
        card.created = DateTime.now().toString()
        setLastLearnedDateString(card)
        flashCardDao.insert(card)
    }

    override suspend fun update(card: FlashCardEntity) {
        card.modified = DateTime.now().toString()
        setLastLearnedDateString(card)
        flashCardDao.update(card)
    }

    override suspend fun delete(card: FlashCardEntity) = flashCardDao.delete(card)

    override suspend fun deleteById(id: Long) = flashCardDao.deleteById(id)

    private fun setLastLearnedDateString(card: FlashCardEntity) {
        card.lastLearnedDateString = DateTime(card.lastLearnedDate).toString()
    }
}