package com.example.nutritiontracker.events

import com.example.nutritiontracker.models.Category
import com.example.nutritiontracker.models.Meal
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.states.screens.MenuScreenState

interface MainEvent {
    data class CategorySelection(val category: Category, val onBack: () -> Unit): MainEvent
    data class MealSelection(val meal: Meal, val onBack: () -> Unit): MainEvent
    data class FilterMealsByCategory(val category: String, val onBack: () -> Unit): MainEvent
    data class FilterMealsByArea(val area: String, val onBack: () -> Unit): MainEvent
    data class FilterMealsByIngredient(val ingredient: String, val onBack: () -> Unit): MainEvent
    data class SetMainScreenState(val state: MainScreenState): MainEvent
    data class SetMenuScreenState(val state: MenuScreenState): MainEvent
    data class SetFilterScreenState(val state: FilterScreenState): MainEvent
    data class SearchMealsByName(val name: String, val onNotFound: () -> Unit): MainEvent
    data class SearchMealsByIngredient(val ingredient: String, val onNotFound: () -> Unit): MainEvent
}
