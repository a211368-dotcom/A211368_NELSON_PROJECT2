package com.example.a211368_nelson_lab4.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.a211368_nelson_lab4.data.UserData

class LabViewModel : ViewModel() {

    var userData by mutableStateOf(UserData())
        private set

    fun setName(name: String) {
        userData = userData.copy(name = name)
    }

    fun setExperiment(exp: String) {
        userData = userData.copy(experiment = exp)
    }

    fun updateExperiment(title: String) { userData = userData.copy(experiment = title)}
    fun updateUserName(name: String) {
        userData = userData.copy(name = name)
    }

    fun updateNote(note: String) {
        userData = userData.copy(note = note)
    }

    var isSubmitted by mutableStateOf(false)
        private set

    fun submitName(name: String) {
        userData = userData.copy(name = name)
        isSubmitted = true
    }
}