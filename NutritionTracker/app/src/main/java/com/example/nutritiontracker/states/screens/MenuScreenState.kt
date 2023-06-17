package com.example.nutritiontracker.states.screens

sealed class MenuScreenState {
    object Default: MenuScreenState()
    object Error: MenuScreenState()
}
