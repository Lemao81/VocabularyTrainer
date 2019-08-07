package com.jueggs.database.storagelayers

import androidx.room.Dao
import androidx.room.Query
import com.jueggs.database.entities.FlashCardEntity

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashcardentity WHERE box_number = :boxNumber AND last_learned_date < :expiryDate")
    fun readByBoxNumberAndExpiryDate(boxNumber: Int, expiryDate: Long): List<FlashCardEntity>
}