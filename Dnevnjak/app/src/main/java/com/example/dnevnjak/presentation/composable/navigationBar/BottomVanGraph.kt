package com.example.dnevnjak.presentation.composable.navigationBar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dnevnjak.presentation.composable.CalendarPage
import com.example.dnevnjak.presentation.composable.DailyPlanPage
import com.example.dnevnjak.presentation.composable.ProfilePage
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavGraph(
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
            ProfilePage()
        }
    }

}


