package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.presentation.composable.navigation.BottomBar

data class NavigationData(
    val lastDestination: String = BottomBar.Menu.route,
    val lastMenuScreenTabIdx: Int = 0,
    val lastFilterScreenTabIdx: Int = 0
)
