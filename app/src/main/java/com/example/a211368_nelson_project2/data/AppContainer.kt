package com.example.a211368_nelson_project2.data

import android.content.Context
import com.example.a211368_nelson_project2.data.journal.JournalRepository
import com.example.a211368_nelson_project2.data.login.UserRepository

//hubungkan repository dengan labviewmodel
interface AppContainer {
    val journalRepository: JournalRepository
    val userRepository: UserRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {

    override val journalRepository: JournalRepository by lazy {

        JournalRepository(
            LabDatabase.getDatabase(context).journalDao()
        )
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(
            LabDatabase.getDatabase(context).userDao()
        )
    }
}