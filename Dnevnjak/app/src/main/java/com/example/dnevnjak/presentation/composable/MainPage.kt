package com.example.dnevnjak.presentation.composable

import android.annotation.SuppressLint
import android.graphics.Color.rgb
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dnevnjak.presentation.composable.navigationBar.BottomBar
import com.example.dnevnjak.presentation.composable.navigationBar.BottomBar.DailyPlan.route
import com.example.dnevnjak.presentation.composable.navigationBar.BottomNavGraph
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPage(
    onLogout: () -> Unit,
    viewModel: MainViewModel = koinViewModel()
){
    val navController = rememberNavController()
    val singleObligationMode by viewModel.singleObligationMode.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when {
        singleObligationMode -> {
            ObligationReviewPage(viewModel)
        }
        navBackStackEntry?.destination?.route == BottomBar.Profile.route -> {
            Scaffold(
                bottomBar = { BottomBar(navController = navController)}
            ) {
                BottomNavGraph(
                    onLogout = onLogout,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
        else -> {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { viewModel.onEvent(ObligationEvent.CreateObligation) },
                        backgroundColor = Color(rgb(33, 33, 33)),
                        contentColor = Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add contact"
                        )
                    }
                },
                bottomBar = { BottomBar(navController = navController)}
            ) {
                BottomNavGraph(
                    onLogout = onLogout,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBar.Calendar,
        BottomBar.DailyPlan,
        BottomBar.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        backgroundColor = PRIMARY_COLOR
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
fun RowScope.AddItem(
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