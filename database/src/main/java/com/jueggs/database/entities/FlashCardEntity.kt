package com.jueggs.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flash_card")
data class FlashCardEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "front_side_text") var frontSideText: String,
    @ColumnInfo(name = "back_side_texts") var backSideTexts: String,
    @ColumnInfo(name = "last_learned_date") var lastLearnedDate: Long,
    @ColumnInfo(name = "box_number") var boxNumber: Int,
    @ColumnInfo(name = "last_learned_date_string") var lastLearnedDateString: String? = null,
    @ColumnInfo(name = "created") var created: String? = null,
    @ColumnInfo(name = "modified") var modified: String? = null
)