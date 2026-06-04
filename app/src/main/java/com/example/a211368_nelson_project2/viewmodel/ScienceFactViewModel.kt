package com.example.a211368_nelson_project2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211368_nelson_project2.data.API.ScienceFact
import com.example.a211368_nelson_project2.data.API.ScienceFactApiClient
import kotlinx.coroutines.launch

class ScienceFactViewModel : ViewModel() {

    var fact by mutableStateOf<ScienceFact?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    init {
        fetchFact()
    }

    fun fetchFact() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = ""

                fact = ScienceFactApiClient.apiService.getRandomFact()

            } catch (e: Exception) {
                errorMessage = "Failed to load fact. Please check your internet connection."
            } finally {
                isLoading = false
            }
        }
    }
}