package com.example.nutritiontracker.events

sealed interface MealEvent{
    data class MealSelection(val idMeal: String): MealEvent
    data class SaveMeal(val idMeal: String): MealEvent
    data class OpenUrl(val url: String): MealEvent
    object CameraRequest: MealEvent
    data class MessageToast(val message: String): MealEvent
    data class SetImageUri(val imageUrl: String): MealEvent
    data class SetVideoUri(val videoUrl: String): MealEvent
    data class SetName(val name: String): MealEvent
    object ResetImageUri: MealEvent
    object ResetVideUri: MealEvent
    object ResetName: MealEvent
}