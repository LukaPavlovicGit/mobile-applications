package com.example.dnevnjak.presentation.events

sealed interface UserEvent{
    data class SetEmail(val email: String): UserEvent
    data class SetUsername(val username: String): UserEvent
    data class SetPassword(val password: String): UserEvent
    object Login: UserEvent
}