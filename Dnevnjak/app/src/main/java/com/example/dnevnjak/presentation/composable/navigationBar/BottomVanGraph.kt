package com.example.dnevnjak.presentation.composable.navigationBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dnevnjak.presentation.composable.CalendarPage

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBar.Calendar.route
    ){
        composable(route = BottomBar.Calendar.route){
            CalendarPage()
        }
        composable(route = BottomBar.DailyPlan.route){

        }
        composable(route = BottomBar.Profile.route){

        }
    }

}
