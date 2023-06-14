package com.example.nutritiontracker.events

import com.example.nutritiontracker.models.Category
import com.example.nutritiontracker.models.Meal
import com.example.nutritiontracker.states.MainScreenState

interface MainEvent {
    data class SetMainScreenState(val mainScreenState: MainScreenState): MainEvent
    data class CategorySelection(val category: Category?): MainEvent
    data class MealSelection(val meal: Meal?): MainEvent
}
