package com.jueggs.database.repositories

import com.jueggs.database.daos.FlashCardDao
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.common.interfaces.FlashCardRepository
import com.jueggs.common.models.FlashCard
import com.jueggs.database.mapper.interfaces.IFlashCardMapper
import org.joda.time.DateTime

class FlashCardRepositoryImpl(
    private val flashCardDao: FlashCardDao,
    private val flashCardMapper: IFlashCardMapper
) : FlashCardRepository {
    override suspend fun readAll() = flashCardDao.readAll().map(flashCardMapper::mapEntityToFlashCard)

    override suspend fun readById(id: Long) = flashCardMapper.mapEntityToFlashCard(flashCardDao.readById(id))

    override suspend fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long) = flashCardDao.readByBoxNumberAndExpiryDate(boxNumber, expiryDate).map(flashCardMapper::mapEntityToFlashCard)

    override suspend fun insert(flashCard: FlashCard) {
        val entity = flashCardMapper.mapFlashCardToEntity(flashCard)
        entity.created = DateTime.now().toString()
        setLastLearnedDateString(entity)
        flashCardDao.insert(entity)
    }

    override suspend fun update(flashCard: FlashCard) {
        val entity = flashCardMapper.mapFlashCardToEntity(flashCard)
        entity.modified = DateTime.now().toString()
        setLastLearnedDateString(entity)
        flashCardDao.update(entity)
    }

    override suspend fun delete(flashCard: FlashCard) {
        val entity = flashCardMapper.mapFlashCardToEntity(flashCard)
        flashCardDao.delete(entity)
    }

    override suspend fun deleteById(id: Long) = flashCardDao.deleteById(id)

    private fun setLastLearnedDateString(flashCard: FlashCardEntity) {
        flashCard.lastLearnedDateString = DateTime(flashCard.lastLearnedDate).toString()
    }
}