package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
private fun SortComponent(
    viewModel: MainViewModel = viewModel()
){
    val options = listOf("NAME", "TODO...")
    val selectedIndex = remember { mutableStateOf(0) }
    val expanded = remember { mutableStateOf(false) }
    val orderBy = remember { mutableStateOf("ASC") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "Sort by: ", fontSize = 16.sp)
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
//                        when(option){
//                            "NAME" -> { viewModel.onEvent(MainEvent.SortMealsListByName) }
//                        }
                    }) {
                        Text(text = option)
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "Order by: ", fontSize = 16.sp)
            Text(
                text = orderBy.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.clickable {
//                    when(orderBy.value){
//                        "ASC" -> {
//                            orderBy.value = "DESC"
//                            viewModel.onEvent(MainEvent.MealsListAscOrder)
//                        }
//                        "DESC" -> {
//                            orderBy.value = "ASC"
//                            viewModel.onEvent(MainEvent.MealsListDescOrder)
//                        }
//                    }
                }
            )
        }
    }
}

@Composable
private fun SearchByNameComponent(
    viewModel: MainViewModel = viewModel()
){
    val text = remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
    ){
        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
//                viewModel.onEvent(MainEvent.SearchMealsListByName(name = it)) //, onBack = { viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.ListOfMealsScreen))
            },
            placeholder = { Text(text = "filter by name") },
            shape = RoundedCornerShape(6.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}
