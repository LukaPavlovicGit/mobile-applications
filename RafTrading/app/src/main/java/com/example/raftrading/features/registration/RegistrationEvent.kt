package com.example.raftrading.features.registration

sealed interface RegistrationEvent{
    data class SetUsername(val username: String): RegistrationEvent
    data class SetEmail(val email: String): RegistrationEvent
    data class SetPassword(val password: String): RegistrationEvent
    data class SetConfirmedPassword(val confirmedPassword: String): RegistrationEvent
    object Submit: RegistrationEvent
    object ResetUiState: RegistrationEvent
}