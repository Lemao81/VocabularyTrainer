package com.jueggs.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashCardEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "front_side_text") val frontSideText: String,
    @ColumnInfo(name = "back_side_texts") val backSideTexts: String,
    @ColumnInfo(name = "last_learned_date") val lastLearnedDate: Long,
    @ColumnInfo(name = "box_number") val boxNumber: Int
)