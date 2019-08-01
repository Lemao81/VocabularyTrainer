package com.jueggs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jueggs.database.entities.FlashCardEntity
import com.jueggs.database.storagelayers.FlashCardStorageLayer

@Database(entities = [FlashCardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFlashCardStorageLayer(): FlashCardStorageLayer
}