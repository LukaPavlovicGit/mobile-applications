package com.example.nutritiontracker.presentation.composable.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nutritiontracker.presentation.composable.CreatePlanScreen
import com.example.nutritiontracker.presentation.composable.MenuScreen
import com.example.nutritiontracker.presentation.composable.StatsScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
){

    NavHost(
        navController = navController,
        startDestination = BottomBar.Menu.route
    ){

        composable(route = BottomBar.Menu.route){
            MenuScreen()
        }
        composable(route = BottomBar.Filter.route){

        }
        composable(route = BottomBar.Stats.route){
            StatsScreen()
        }
        composable(route = BottomBar.Plan.route){
            CreatePlanScreen()
        }
    }

}


