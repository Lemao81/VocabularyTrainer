package com.jueggs.database.storagelayers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.jueggs.database.entities.FlashCardEntity

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashcardentity WHERE id = :id")
    fun readById(id: Long): FlashCardEntity

    @Query("SELECT * FROM flashcardentity WHERE box_number = :boxNumber AND last_learned_date < :expiryDate")
    fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>

    @Update
    fun update(card: FlashCardEntity)

    @Delete
    fun delete(card: FlashCardEntity)
}