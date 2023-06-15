package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
){
    NavigationScreen()
}
