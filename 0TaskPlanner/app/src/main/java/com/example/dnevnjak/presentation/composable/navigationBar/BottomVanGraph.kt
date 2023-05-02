package com.example.dnevnjak.presentation.composable.navigationBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dnevnjak.presentation.composable.CalendarPage
import com.example.dnevnjak.presentation.composable.DailyPlanPage
import com.example.dnevnjak.presentation.composable.ProfilePage
import com.example.dnevnjak.presentation.viewModels.MainViewModel

@Composable
fun BottomNavGraph(
    onLogout: () -> Unit,
    navController: NavHostController,
    viewModel: MainViewModel
){
    NavHost(
        navController = navController,
        startDestination =  BottomBar.Calendar.route
    ){

        composable(route = BottomBar.Calendar.route){
            CalendarPage(viewModel = viewModel, onClick = { navController.navigate(BottomBar.DailyPlan.route) })
        }
        composable(route = BottomBar.DailyPlan.route){
            DailyPlanPage(viewModel = viewModel)
        }
        composable(route = BottomBar.Profile.route){
            ProfilePage(onLogout = onLogout)
        }
    }

}


