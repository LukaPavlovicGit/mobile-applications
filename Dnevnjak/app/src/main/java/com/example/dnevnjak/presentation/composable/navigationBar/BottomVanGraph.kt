package com.example.dnevnjak.presentation.composable.navigationBar

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dnevnjak.presentation.composable.CalendarPage
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.states.ObligationState

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    state: ObligationState,
    onEvent: (ObligationEvent) -> Unit
){
    NavHost(
        navController = navController,
        startDestination = BottomBar.Calendar.route,
        modifier = Modifier.background(PRIMARY_COLOR)
    ){
        composable(route = BottomBar.Calendar.route){
            CalendarPage(state = state, onEvent = onEvent)
        }
        composable(route = BottomBar.DailyPlan.route){

        }
        composable(route = BottomBar.Profile.route){

        }
    }

}


