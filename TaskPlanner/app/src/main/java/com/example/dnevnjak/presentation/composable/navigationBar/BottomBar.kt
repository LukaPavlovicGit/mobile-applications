package com.example.dnevnjak.presentation.composable.navigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Calendar : BottomBar(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.CalendarMonth
    )

    object DailyPlan : BottomBar(
        route = "dailyPlan",
        title = "Daily plan",
        icon = Icons.Default.ListAlt
    )

    object Profile : BottomBar(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person,
    )
}
