package com.example.a211368_nelson_project2.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211368_nelson_project2.data.JournalEntry
import com.example.a211368_nelson_project2.data.JournalRepository
import com.example.a211368_nelson_project2.data.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// sebab ada constructor, tak boleh guna macam biasa, kena buat factory (appviewmodelprovider)
class LabViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {

    var userData by mutableStateOf(UserData())
        private set

    var lastUpdated by mutableStateOf("")
        private set

    val noteHistory = mutableStateListOf<String>()

    var profileEmail by mutableStateOf("")
        private set

    var profileAge by mutableStateOf("")
        private set

    var profileClass by mutableStateOf("")
        private set

    var profileImage by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    var isSubmitted by mutableStateOf(false)
        private set

    // Assignment Calculator state
    var assignmentMark by mutableStateOf("")
        private set

    var assignmentTotal by mutableStateOf("")
        private set

    var assignmentWeight by mutableStateOf("")
        private set

    var assignmentResult by mutableStateOf("")
        private set

    val isCalculatorInputValid: Boolean
        get() = assignmentMark.isNotBlank() &&
                assignmentTotal.isNotBlank() &&
                assignmentWeight.isNotBlank()

    val journalEntries = journalRepository.getAllEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateAssignmentMark(value: String) {
        assignmentMark = value
    }

    fun updateAssignmentTotal(value: String) {
        assignmentTotal = value
    }

    fun updateAssignmentWeight(value: String) {
        assignmentWeight = value
    }

    fun calculateAssignment() {
        val mark = assignmentMark.toDoubleOrNull()
        val total = assignmentTotal.toDoubleOrNull()
        val weight = assignmentWeight.toDoubleOrNull()

        if (mark != null && total != null && weight != null && total != 0.0) {
            val result = (mark / total) * weight
            assignmentResult = "Result: %.2f / %.2f".format(result, weight)
        } else {
            assignmentResult = "Invalid input"
        }
    }

    fun setName(name: String) {
        userData = userData.copy(name = name)
    }

    fun setExperiment(exp: String) {
        userData = userData.copy(experiment = exp)
    }

    fun updateExperiment(title: String) {
        userData = userData.copy(experiment = title)
    }

    fun updateUserName(name: String) {
        userData = userData.copy(name = name)
    }

    fun updateNote(note: String) {
        userData = userData.copy(note = note)
    }

    fun saveNote() {
        val currentNote = userData.note

        if (currentNote.isNotBlank() && currentNote != noteHistory.lastOrNull()) {
            noteHistory.add(currentNote)
        }

        lastUpdated = SimpleDateFormat(
            "dd MMM yyyy, HH:mm",
            Locale.getDefault()
        ).format(Date())
    }

    fun saveNoteToRoom() {
        viewModelScope.launch {
            val currentNote = userData.note

            if (currentNote.isNotBlank()) {
                journalRepository.insertEntry(
                    JournalEntry(
                        experimentName = userData.experiment,
                        note = currentNote,
                        date = SimpleDateFormat(
                            "dd MMM yyyy, HH:mm",
                            Locale.getDefault()
                        ).format(Date())
                    )
                )
            }
        }
    }

    fun submitName(name: String) {
        userData = userData.copy(name = name)
        isSubmitted = true
    }

    fun updateProfile(email: String, age: String, className: String) {
        profileEmail = email
        profileAge = age
        profileClass = className

        userData = userData.copy(
            email = email,
            age = age,
            className = className
        )
    }

    fun updateProfileImage(image: String) {
        profileImage = image
    }

    fun login(name: String) {
        userData = userData.copy(name = name)
        isLoggedIn = true
    }
}