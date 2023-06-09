package com.example.raftrading.features.login

sealed interface LoginEvent {
    data class SetEmail(val email: String): LoginEvent
    data class SetPassword(val password: String): LoginEvent
    object Submit: LoginEvent
    object ResetUiState: LoginEvent
}