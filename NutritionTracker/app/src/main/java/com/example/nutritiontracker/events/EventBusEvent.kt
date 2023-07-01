package com.example.nutritiontracker.events

sealed interface EventBusEvent {
    data class OpenUrl(val url: String): EventBusEvent
    data class MessageToast(val message: String): EventBusEvent
    object CameraRequest: EventBusEvent
    data class MealImageUrl(val url: String): EventBusEvent
    data class SendEmail(val receiver: String, val subject: String, val body: String): EventBusEvent
}