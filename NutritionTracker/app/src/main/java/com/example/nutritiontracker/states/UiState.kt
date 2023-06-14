package com.example.nutritiontracker.states


sealed class UiState {
    object Nothing: UiState()
    object Processing: UiState()
    data class Success(val message: String): UiState()
    data class Failure(val message: String): UiState()
}