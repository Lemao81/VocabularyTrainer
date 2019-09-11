package com.jueggs.database.repositories

import com.jueggs.database.daos.IFlashCardDao
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.mapper.interfaces.IFlashCardMapper
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.interfaces.IFlashCardRepository
import com.jueggs.domain.models.FlashCard
import org.joda.time.DateTime

class FlashCardRepository(
    private val flashCardDao: IFlashCardDao,
    private val flashCardMapper: IFlashCardMapper
) : IFlashCardRepository {
    override suspend fun readAll() = flashCardDao.readAll().map(flashCardMapper::mapEntityToFlashCard)

    override suspend fun readById(id: Long) = flashCardMapper.mapEntityToFlashCard(flashCardDao.readById(id))

    override suspend fun readByBoxAndExpiryDate(box: FlashCardBox, expiryDate: Long) =
        flashCardDao.readByBoxNumberAndExpiryDate(box.number, expiryDate).map(flashCardMapper::mapEntityToFlashCard)

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