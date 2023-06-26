package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.viewModel.MainViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun UpdateMealScreen(
    viewModel: MainViewModel = viewModel(),
    onBack: () -> Unit,
    openCamera: () -> Unit
){

    val mainDataState = viewModel.mainDataState.collectAsState()
    val meal = mainDataState.value.mealById
    val date = remember { mutableStateOf(LocalDate.now()) }
    val timeDialogState = rememberMaterialDialogState()
    val options = listOf(
        MealType.Breakfast.name,
        MealType.Launch.name,
        MealType.Dinner.name,
        MealType.Snack.name)
    val expanded = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(date.value)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {


            AsyncImage(
                model = meal!!.meals[0].strMealThumb,
                contentDescription = "description",
                modifier = Modifier.clickable { openCamera.invoke() }
            )

            Text(text = meal.meals[0].strMeal, fontSize = 25.sp)
            Text(
                text = options[selectedIndex.value],
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.clickable { expanded.value = true },
                style = MaterialTheme.typography.body1
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.clickable { expanded.value = true }
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(onClick = {
                        selectedIndex.value = index
                        expanded.value = false


                    }) {
                        Text(text = option)
                    }
                }
            }

            OutlinedTextField(
                enabled = false,
                value = formattedTime,
                onValueChange = {  },
                modifier = Modifier
                    .clickable { timeDialogState.show() },
                singleLine = true,
                label = { Text(text = "Date to eat", fontSize = 18.sp, color = Color.Yellow) },
                textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Black)
            )

            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(text = "Ok")
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker (
                    title = "Pick a date"
                ) {
                    date.value = it
                }
            }

            Button(
                onClick = {  },
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = "Save", fontSize = 20.sp)
            }

            Button(
                onClick = { onBack.invoke() },
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = "Back", fontSize = 20.sp)
            }

        }
    }

}