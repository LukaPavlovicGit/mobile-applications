package com.example.nutritiontracker.states.screens


sealed class LoginScreenState {
    object Default: LoginScreenState()
    object Success: LoginScreenState()
    data class Failure(val message: String? = null): LoginScreenState()
    object Processing: LoginScreenState()
}
