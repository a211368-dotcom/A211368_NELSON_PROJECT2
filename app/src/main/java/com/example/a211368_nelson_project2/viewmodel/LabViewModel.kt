package com.example.a211368_nelson_project2.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211368_nelson_project2.data.journal.JournalEntry
import com.example.a211368_nelson_project2.data.journal.JournalRepository
import com.example.a211368_nelson_project2.data.UserData
import com.example.a211368_nelson_project2.data.login.UserEntity
import com.example.a211368_nelson_project2.data.login.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// sebab ada constructor, tak boleh guna macam biasa, kena buat factory (appviewmodelprovider)
class LabViewModel(
    private val journalRepository: JournalRepository,
    private val userRepository: UserRepository
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

    var loginMessage by mutableStateOf("")
        private set

    var currentUser by mutableStateOf<UserEntity?>(null)
        private set

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

        viewModelScope.launch {
            val username = userData.name

            if (username.isNotBlank()) {
                val existingUser = userRepository.getUser(username)

                if (existingUser != null) {
                    val updatedUser = existingUser.copy(
                        email = email,
                        age = age,
                        className = className,
                        profileImage = profileImage
                    )

                    userRepository.updateUser(updatedUser)
                    currentUser = updatedUser
                }
            }
        }
    }

    fun updateProfileImage(image: String) {
        profileImage = image
    }


    fun registerUser(
        username: String,
        password: String,
        email: String,
        age: String,
        className: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            if (
                username.isBlank() ||
                password.isBlank() ||
                email.isBlank() ||
                age.isBlank() ||
                className.isBlank()
            ) {
                loginMessage = "Please fill in all fields"
                onResult(false)
                return@launch
            }

            val existingUser = userRepository.getUser(username)

            if (existingUser != null) {
                loginMessage = "Username already exists"
                onResult(false)
            } else {
                val newUser = UserEntity(
                    username = username,
                    password = password,
                    email = email,
                    age = age,
                    className = className
                )

                userRepository.registerUser(newUser)

                currentUser = newUser

                userData = userData.copy(
                    name = username,
                    email = email,
                    age = age,
                    className = className
                )

                profileEmail = email
                profileAge = age
                profileClass = className
                isLoggedIn = true
                loginMessage = "Register successful"

                onResult(true)
            }
        }
    }

    fun login(
        username: String,
        password: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val user = userRepository.loginUser(username, password)

            if (user != null) {
                currentUser = user

                userData = userData.copy(
                    name = user.username,
                    email = user.email,
                    age = user.age,
                    className = user.className
                )

                profileImage = user.profileImage
                isLoggedIn = true
                loginMessage = "Login successful"

                onResult(true)
            } else {
                loginMessage = "Invalid username or password"
                onResult(false)
            }
        }
    }
}