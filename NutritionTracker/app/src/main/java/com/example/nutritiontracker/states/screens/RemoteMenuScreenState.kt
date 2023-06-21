package com.example.nutritiontracker.states.screens

sealed class RemoteMenuScreenState {
    object Default: RemoteMenuScreenState()
    object Error: RemoteMenuScreenState()
}
