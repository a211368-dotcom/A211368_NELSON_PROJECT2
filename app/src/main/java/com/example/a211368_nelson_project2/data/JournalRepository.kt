package com.example.a211368_nelson_project2.data

import kotlinx.coroutines.flow.Flow

class JournalRepository(
    private val journalDao: JournalDao
) {

    fun getAllEntries(): Flow<List<JournalEntry>> {
        return journalDao.getAllEntries()
    }

    suspend fun insertEntry(entry: JournalEntry) {
        journalDao.insert(entry)
    }
}