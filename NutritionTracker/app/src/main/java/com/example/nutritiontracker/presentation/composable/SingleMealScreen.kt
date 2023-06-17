package com.example.nutritiontracker.presentation.composable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun SingleMealScreen(
    viewModel: MainViewModel = viewModel(),
    onBack: () -> Unit
){

    Column() {
        Button(
            onClick = { onBack.invoke() },
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
    }

}
