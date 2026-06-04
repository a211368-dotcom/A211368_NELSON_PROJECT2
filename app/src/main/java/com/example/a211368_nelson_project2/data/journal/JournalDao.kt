package com.example.a211368_nelson_project2.data.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert
    suspend fun insert(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>
}