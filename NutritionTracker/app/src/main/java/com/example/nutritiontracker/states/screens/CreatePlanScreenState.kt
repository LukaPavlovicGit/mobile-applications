package com.example.nutritiontracker.states.screens

sealed class CreatePlanScreenState{
    object PeriodSelection: CreatePlanScreenState()
    object MealSelection: CreatePlanScreenState()
    object Email: CreatePlanScreenState()
}
