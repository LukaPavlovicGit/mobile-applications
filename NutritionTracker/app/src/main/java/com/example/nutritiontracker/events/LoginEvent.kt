package com.example.nutritiontracker.events

sealed interface LoginEvent {
    data class SetEmail(val email: String): LoginEvent
    data class SetPassword(val password: String): LoginEvent
    object Submit: LoginEvent
    object ResetUiState: LoginEvent
}