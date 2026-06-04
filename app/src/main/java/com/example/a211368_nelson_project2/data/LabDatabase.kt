package com.example.a211368_nelson_project2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a211368_nelson_project2.data.journal.JournalDao
import com.example.a211368_nelson_project2.data.journal.JournalEntry
import com.example.a211368_nelson_project2.data.login.UserDao
import com.example.a211368_nelson_project2.data.login.UserEntity

@Database(
    entities = [JournalEntry::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class LabDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: LabDatabase? = null

        fun getDatabase(context: Context): LabDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LabDatabase::class.java,
                    "lab_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}