package com.example.a211368_nelson_project2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val experimentName: String,

    val note: String,

    val date: String
)