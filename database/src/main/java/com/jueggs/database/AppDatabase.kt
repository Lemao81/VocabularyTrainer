package com.jueggs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jueggs.database.daos.IFlashCardDao
import com.jueggs.database.entities.FlashCardEntity

@Database(entities = [FlashCardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFlashCardDao(): IFlashCardDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val NAME = "vocabularytrainer.db"

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return INSTANCE!!
        }
    }
}