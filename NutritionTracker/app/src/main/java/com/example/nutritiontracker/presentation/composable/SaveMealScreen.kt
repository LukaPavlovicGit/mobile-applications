package com.example.nutritiontracker.presentation.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadingScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.screens.SaveMealScreenState
import com.example.nutritiontracker.states.screens.SingleMealScreenState
import com.example.nutritiontracker.viewModel.MainViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SaveMealScreen(
    viewModel: MainViewModel = viewModel(),
    openCamera: () -> Unit
){

    val saveScreenState = viewModel.saveMealScreenState.collectAsState()
    when(saveScreenState.value){
        SaveMealScreenState.Default -> DefaultSaveMealScreen(openCamera = openCamera)
        SaveMealScreenState.Processing -> LoadingScreen()
        is SaveMealScreenState.Success -> {
            toast(context = LocalContext.current, message = "SAVED SUCCESSFULLY")
            (saveScreenState.value as SaveMealScreenState.Success).onSuccess.invoke()
        }
        is SaveMealScreenState.Error -> {
            toast(context = LocalContext.current, message = "ERROR")
            (saveScreenState.value as SaveMealScreenState.Error).onError.invoke()
        }
    }
}

@Composable
private fun DefaultSaveMealScreen(
    viewModel: MainViewModel = viewModel(),
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
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = meal!!.meals[0].strMealThumb,
                contentDescription = "description",
                modifier = Modifier.size(250.dp).padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp).clickable { openCamera.invoke() }
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
            ) {
                Text(text = "NAME:", fontWeight = FontWeight.SemiBold, fontSize = 22.sp, modifier = Modifier.padding(end = 15.dp))
                Text(text = meal.meals[0].strMeal, fontSize = 20.sp)
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
            ) {
                Text(text = "MEAL TYPE:", fontWeight = FontWeight.SemiBold, fontSize = 22.sp, modifier = Modifier.padding(end = 15.dp))
                Text(
                    text = options[selectedIndex.value],
                    fontSize = 25.sp,
                    modifier = Modifier.clickable { expanded.value = true },
                    style = MaterialTheme.typography.body1
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                    .clickable { openCamera.invoke() }
            ) {
                OutlinedTextField(
                    enabled = false,
                    value = formattedTime,
                    onValueChange = {  },
                    modifier = Modifier
                        .clickable { timeDialogState.show() },
                    singleLine = true,
                    label = { Text(text = "Date to eat", fontSize = 18.sp, color = Color.Red) },
                    textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Black)
                )
            }

            Spacer(modifier = Modifier.size(50.dp))
            Button(
                onClick = {
                    viewModel.onEvent(MainEvent.SaveMeal(dataToEat = date.value, mealType = MealType.valueOf(options[selectedIndex.value])))
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text(text = "Save", fontSize = 20.sp)
            }

            Button(
                onClick = {
                    viewModel.onEvent(MainEvent.SetSingleMealScreenState(SingleMealScreenState.Default))
                },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text(text = "Back", fontSize = 20.sp)
            }



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

        }
    }
}