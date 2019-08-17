package com.jueggs.database.daos

import androidx.room.*
import com.jueggs.database.entities.FlashCardEntity

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flash_card")
    fun readAll(): List<FlashCardEntity>

    @Query("SELECT * FROM flash_card WHERE id = :id")
    fun readById(id: Long): FlashCardEntity

    @Query("SELECT * FROM flash_card WHERE box_number = :boxNumber AND last_learned_date < :expiryDate")
    fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: FlashCardEntity)

    @Update
    fun update(card: FlashCardEntity)

    @Delete
    fun delete(card: FlashCardEntity)

    @Query("DELETE FROM flash_card WHERE id = :id")
    fun deleteById(id: Long)
}