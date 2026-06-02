package com.example.a211368_nelson_project2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [JournalEntry::class],
    version = 1,
    exportSchema = false
)
abstract class LabDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao

    companion object {

        @Volatile
        private var INSTANCE: LabDatabase? = null

        fun getDatabase(context: Context): LabDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LabDatabase::class.java,
                    "lab_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}