package com.example.nutritiontracker.presentation.composable.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nutritiontracker.presentation.composable.CreatePlanScreen
import com.example.nutritiontracker.presentation.composable.FilterScreen
import com.example.nutritiontracker.presentation.composable.MenuScreen
import com.example.nutritiontracker.presentation.composable.StatsScreen
import com.example.nutritiontracker.viewModel.MainViewModel

// QUESTION: why do I need to pass viewModel from this composable down to every child ??!!
@Composable
fun BottomNavGraph(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController,
    startDestination: String = BottomBar.Menu.route,
    menuScreenTabIdx: Int,
    filterScreenTabIdx: Int
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        composable(route = BottomBar.Menu.route){
            MenuScreen(viewModel = viewModel, menuScreenTabIdx = menuScreenTabIdx)
        }
        composable(route = BottomBar.Filter.route){
            FilterScreen(viewModel = viewModel, filterScreenTabIdx = filterScreenTabIdx)
        }
        composable(route = BottomBar.Stats.route){
            StatsScreen(viewModel = viewModel)
        }
        composable(route = BottomBar.Plan.route){
            CreatePlanScreen(viewModel = viewModel)
        }
    }

}


