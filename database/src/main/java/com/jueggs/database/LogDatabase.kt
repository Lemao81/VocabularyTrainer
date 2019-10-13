package com.jueggs.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LogEntryEntity::class], version = 1)
abstract class LogDatabase : RoomDatabase() {
    abstract fun getLogDao(): ILogDao

    companion object {
        private var INSTANCE: LogDatabase? = null
        private const val NAME = "logs.db"

        fun getInstance(context: Context): LogDatabase {
            if (INSTANCE == null) {
                synchronized(LogDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LogDatabase::class.java, NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return INSTANCE!!
        }
    }
}

@Dao
interface ILogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: LogEntryEntity)
}

@Entity(tableName = "log_entries")
data class LogEntryEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "logLevel") var logLevel: String,
    @ColumnInfo(name = "message") var message: String?,
    @ColumnInfo(name = "method") var method: String,
    @ColumnInfo(name = "timestamp_string") var timestampString: String,
    @ColumnInfo(name = "timestamp_millis") var timestampMillis: Long,
    @ColumnInfo(name = "values") var values: String?,
    @ColumnInfo(name = "exception") var exception: String?
)