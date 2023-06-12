package com.example.nutritiontracker.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.presentation.UiState
import com.example.nutritiontracker.viewModel.CategoriesViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = viewModel()
){

    val uiState = viewModel.categoriesUiState.collectAsState()
    when(uiState.value){
        is UiState.Failure -> TODO()
        UiState.Nothing -> TODO()
        UiState.Processing -> CircularIndeterminateProgressBar()
        is UiState.Success -> CategoriesList()
    }
}

@Composable
private fun CategoriesList(
    viewModel: CategoriesViewModel = viewModel()
){
    val categoriesDataState = viewModel.categoriesDataState.collectAsState()

}