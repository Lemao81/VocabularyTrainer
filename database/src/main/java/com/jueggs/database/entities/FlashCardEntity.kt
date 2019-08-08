package com.jueggs.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashCardEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "front_side_text") var frontSideText: String,
    @ColumnInfo(name = "back_side_texts") var backSideTexts: String,
    @ColumnInfo(name = "last_learned_date") var lastLearnedDate: Long,
    @ColumnInfo(name = "box_number") var boxNumber: Int
)