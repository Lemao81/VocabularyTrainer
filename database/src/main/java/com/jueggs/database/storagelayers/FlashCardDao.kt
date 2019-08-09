package com.jueggs.database.storagelayers

import androidx.room.*
import com.jueggs.database.entities.FlashCardEntity

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashcardentity WHERE id = :id")
    fun readById(id: Long): FlashCardEntity

    @Query("SELECT * FROM flashcardentity WHERE box_number = :boxNumber AND last_learned_date < :expiryDate")
    fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: FlashCardEntity)

    @Update
    fun update(card: FlashCardEntity)

    @Delete
    fun delete(card: FlashCardEntity)
}