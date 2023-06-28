package com.example.nutritiontracker.states.requests

sealed class EmailRequest{
    object Success: EmailRequest()
    data class Failure(val message: String? = null): EmailRequest()
}
