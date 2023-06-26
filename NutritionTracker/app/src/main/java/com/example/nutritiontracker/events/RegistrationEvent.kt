package com.example.nutritiontracker.events

import com.example.nutritiontracker.states.screens.RegistrationScreenState

sealed interface RegistrationEvent{
    data class SetUsername(val username: String): RegistrationEvent
    data class SetEmail(val email: String): RegistrationEvent
    data class SetPassword(val password: String): RegistrationEvent
    data class SetConfirmedPassword(val confirmedPassword: String): RegistrationEvent
    object Submit: RegistrationEvent
    data class SetRegistrationScreenState(val state: RegistrationScreenState): RegistrationEvent
}