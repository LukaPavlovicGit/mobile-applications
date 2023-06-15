package com.example.nutritiontracker.events

import com.example.nutritiontracker.models.Category
import com.example.nutritiontracker.models.Meal
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.screens.MenuScreenState

interface MainEvent {
    data class CategorySelection(val category: Category?): MainEvent
    data class MealSelection(val meal: Meal?): MainEvent
    data class FilterMealsByCategory(val category: String): MainEvent
    data class FilterMealsByArea(val area: String): MainEvent
    data class FilterMealsByIngredient(val ingredient: String): MainEvent
    data class SetMenuScreenState(val state: MenuScreenState): MainEvent
    data class SetFilterScreenState(val state: FilterScreenState): MainEvent
}
