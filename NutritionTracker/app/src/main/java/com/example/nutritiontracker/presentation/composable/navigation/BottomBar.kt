package com.example.nutritiontracker.presentation.composable.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Menu : BottomBar(
        route = "menu",
        title = "Menu",
        icon = Icons.Default.RestaurantMenu
    )

    object Filter : BottomBar(
        route = "filter",
        title = "Filter",
        icon = Icons.Default.FilterAlt
    )

    object Stats : BottomBar(
        route = "stats",
        title = "Statistics",
        icon = Icons.Default.QueryStats,
    )

    object Plan : BottomBar(
        route = "createPlan",
        title = "Plan",
        icon = Icons.Default.Build,
    )
}
