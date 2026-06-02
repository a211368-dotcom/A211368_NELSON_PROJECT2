package com.example.a211368_nelson_project2.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a211368_nelson_project2.LabApplication

object AppViewModelProvider {

    val Factory = viewModelFactory {

        initializer {
            LabViewModel(
                labApplication().container.journalRepository
            )
        }
    }
}

fun CreationExtras.labApplication(): LabApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as LabApplication)