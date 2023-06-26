package com.example.nutritiontracker.states.requests

sealed class AuthRequestState{
    object Success: AuthRequestState()
    data class Failure(val message: String? = null): AuthRequestState()
}
