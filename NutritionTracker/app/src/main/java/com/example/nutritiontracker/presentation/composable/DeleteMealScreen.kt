package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.presentation.composable.cammon.LoadingScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.screens.DeleteMealScreenState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun DeleteMealScreen(
    viewModel: MainViewModel = viewModel(),
){

    val deleteMealScreenState = viewModel.deleteMealScreenState.collectAsState()
    when(deleteMealScreenState.value){
        DeleteMealScreenState.Processing -> LoadingScreen()
        is DeleteMealScreenState.Success -> {
            toast(context = LocalContext.current, message = "DELETED SUCCESSFULLY")
            (deleteMealScreenState.value as DeleteMealScreenState.Success).onSuccess.invoke()
        }
        is DeleteMealScreenState.Error -> {
            toast(context = LocalContext.current, message = "ERROR")
            (deleteMealScreenState.value as DeleteMealScreenState.Error).onError.invoke()
        }
    }

}