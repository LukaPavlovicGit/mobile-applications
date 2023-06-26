package com.example.nutritiontracker.states.screens


sealed class RegistrationScreenState {
    object Default: RegistrationScreenState()
    object Success: RegistrationScreenState()
    data class Failure(val message: String? = null): RegistrationScreenState()
    object Processing: RegistrationScreenState()
}
