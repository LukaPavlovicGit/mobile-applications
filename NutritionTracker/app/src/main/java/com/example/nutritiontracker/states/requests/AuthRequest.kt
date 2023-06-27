package com.example.nutritiontracker.states.requests

sealed class AuthRequest{
    object Success: AuthRequest()
    data class Failure(val message: String? = null): AuthRequest()
}
