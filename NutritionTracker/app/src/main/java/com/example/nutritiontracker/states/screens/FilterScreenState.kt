package com.example.nutritiontracker.states.screens

sealed class FilterScreenState {
    object Default: FilterScreenState()
    object Error: FilterScreenState()
}
