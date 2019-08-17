package com.jueggs.database.repositories

import com.jueggs.database.daos.FlashCardDao
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import org.joda.time.DateTime

class FlashCardRepositoryImpl(
    private val flashCardDao: FlashCardDao
) : FlashCardRepository {
    override fun readAll() = flashCardDao.readAll()

    override fun readById(id: Long) = flashCardDao.readById(id)

    override fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long) = flashCardDao.readByBoxNumberAndExpiryDate(boxNumber, expiryDate)

    override fun insert(card: FlashCardEntity) {
        card.created = DateTime.now().toString()
        setLastLearnedDateString(card)
        flashCardDao.insert(card)
    }

    override fun update(card: FlashCardEntity) {
        card.modified = DateTime.now().toString()
        setLastLearnedDateString(card)
        flashCardDao.update(card)
    }

    override fun delete(card: FlashCardEntity) = flashCardDao.delete(card)

    override fun deleteById(id: Long) = flashCardDao.deleteById(id)

    private fun setLastLearnedDateString(card: FlashCardEntity) {
        card.lastLearnedDateString = DateTime(card.lastLearnedDate).toString()
    }
}