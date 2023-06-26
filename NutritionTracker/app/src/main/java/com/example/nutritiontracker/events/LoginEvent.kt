package com.example.nutritiontracker.events

import com.example.nutritiontracker.states.screens.LoginScreenState

sealed interface LoginEvent {
    data class SetEmail(val email: String): LoginEvent
    data class SetPassword(val password: String): LoginEvent
    object Submit: LoginEvent
    data class SetLoginScreenState(val state: LoginScreenState): LoginEvent
}