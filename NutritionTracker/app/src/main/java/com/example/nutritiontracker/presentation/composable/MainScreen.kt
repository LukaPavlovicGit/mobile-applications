package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutritiontracker.presentation.composable.navigation.BottomBar
import com.example.nutritiontracker.presentation.composable.navigation.BottomNavGraph
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
){
    val navController = rememberNavController()
    val shouldShowNavigationBar = viewModel.shouldShowNavigationBar.collectAsState()

    Scaffold(
        bottomBar = {
            if(shouldShowNavigationBar.value){
                BottomBar(navController = navController)
            }
        },
        content = { paddingValues ->
            Row(modifier = Modifier.padding(paddingValues)){
                BottomNavGraph(
                    navController = navController
                )
            }
        }
    )
}

@Composable
private fun BottomBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        BottomBar.Menu,
        BottomBar.Plan,
        BottomBar.Stats,
        BottomBar.Profile
    )
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
private fun RowScope.AddItem(
    viewModel: MainViewModel = viewModel(),
    screen: BottomBar,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}
