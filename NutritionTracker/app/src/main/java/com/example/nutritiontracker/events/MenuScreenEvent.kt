package com.example.nutritiontracker.events


sealed interface MenuScreenEvent {

    data class SearchMealsByCategory(val category: String): MenuScreenEvent
    data class SearchMealsByArea(val area: String): MenuScreenEvent
    data class SearchMealsByIngredient(val ingredient: String): MenuScreenEvent
    data class SearchMealsByName(val name: String): MenuScreenEvent
    data class SearchMealsByTags(val tags: String): MenuScreenEvent
}