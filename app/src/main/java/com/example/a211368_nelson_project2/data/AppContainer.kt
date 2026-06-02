package com.example.a211368_nelson_project2.data

import android.content.Context

//hubungkan repository dengan labviewmodel
interface AppContainer {
    val journalRepository: JournalRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {

    override val journalRepository: JournalRepository by lazy {

        JournalRepository(
            LabDatabase.getDatabase(context).journalDao()
        )
    }
}