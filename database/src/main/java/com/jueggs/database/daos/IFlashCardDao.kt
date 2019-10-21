package com.jueggs.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jueggs.database.entities.FlashCardEntity

@Dao
interface IFlashCardDao {
    @Query("SELECT * FROM flash_card")
    fun readAll(): List<FlashCardEntity>

    @Query("SELECT * FROM flash_card WHERE id = :id")
    fun readById(id: Long): FlashCardEntity

    @Query("SELECT * FROM flash_card WHERE box_number = :boxNumber AND last_learned_date < :expiryDate")
    fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: FlashCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cards: List<FlashCardEntity>)

    @Update
    fun update(card: FlashCardEntity)

    @Delete
    fun delete(card: FlashCardEntity)

    @Query("DELETE FROM flash_card WHERE id = :id")
    fun deleteById(id: Long)
}