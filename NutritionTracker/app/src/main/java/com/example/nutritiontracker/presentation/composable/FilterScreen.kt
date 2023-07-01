package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
private fun TabScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val tabOptions = listOf("Categories", "Areas", "Ingredients")
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.height(40.dp),
            backgroundColor = Color.DarkGray,
            contentColor = Color.White
        ) {
            tabOptions.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        selectedTabIndex.value = index
                    }
                ) {
                    Text(text = title)
                }
            }
        }

        when (selectedTabIndex.value) {
            0 -> CategoriesTab()
            1 -> AreasTab()
            2 -> IngredientsTab()
        }
    }
}

@Composable
private fun CategoriesTab(
    viewModel: MainViewModel = hiltViewModel()
) {
//    val energy = viewModel.filterScreenEnergyData.collectAsState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            }

//            if(energy.value.categoryNames != null){
//                DropdownMenu(
//                    expanded = expanded.value,
//                    onDismissRequest = { expanded.value = false }
//                ) {
//                    energy.value.categoryNames!!.forEach { opt ->
//                        DropdownMenuItem(onClick = {
//                            expanded.value = false
//                            inputText.value = opt
//                        }) {
//                            Text(text = opt)
//                        }
//                    }
//                }
//            }
            TextField(
                value = inputText.value,
                label = { Text(text = "search by category") },
                placeholder = { Text(text = "search by category") },
                onValueChange = { inputText.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        selectedOption.value = inputText.value
                        inputText.value = ""
                    }
                )
            )
        }
        Spacer(modifier = Modifier.size(100.dp))
        Button(
            onClick = {
//                viewModel.onEvent(MainEvent.FilterMealsByCategory(category = inputText.value))
                      },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
    }
}

@Composable
private fun AreasTab(
    viewModel: MainViewModel = hiltViewModel()
) {
//    val energy = viewModel.filterScreenEnergyData.collectAsState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            }

//            if(energy.value.areaNames != null){
//                DropdownMenu(
//                    expanded = expanded.value,
//                    onDismissRequest = { expanded.value = false }
//                ) {
//                    energy.value.areaNames!!.forEach { opt ->
//                        DropdownMenuItem(onClick = {
//                            expanded.value = false
//                            inputText.value = opt
//                        }) {
//                            Text(text = opt)
//                        }
//                    }
//                }
//            }
            TextField(
                value = inputText.value,
                label = { Text(text = "search by area") },
                placeholder = { Text(text = "search by area") },
                onValueChange = { inputText.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        selectedOption.value = inputText.value
                        inputText.value = ""
                    }
                )
            )
        }
        Spacer(modifier = Modifier.size(100.dp))
        Button(
            onClick = {
//                viewModel.onEvent(MainEvent.FilterMealsByArea(area = inputText.value))
            },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
    }
}

@Composable
private fun IngredientsTab(
    viewModel: MainViewModel = hiltViewModel()
) {
//    val energy = viewModel.filterScreenEnergyData.collectAsState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            }

//            if(energy.value.ingredientsNames != null){
//                DropdownMenu(
//                    expanded = expanded.value,
//                    onDismissRequest = { expanded.value = false }
//                ) {
//                    energy.value.ingredientsNames!!.forEach { opt ->
//                        DropdownMenuItem(onClick = {
//                            expanded.value = false
//                            inputText.value = opt
//                        }) {
//                            Text(text = opt)
//                        }
//                    }
//                }
//            }
            TextField(
                value = inputText.value,
                label = { Text(text = "search by ingredient") },
                placeholder = { Text(text = "search by ingredient") },
                onValueChange = { inputText.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        selectedOption.value = inputText.value
                        inputText.value = ""
                    }
                )
            )
        }
        Spacer(modifier = Modifier.size(100.dp))
        Button(
            onClick = {
//                viewModel.onEvent(MainEvent.FilterMealsByIngredient(ingredient = inputText.value))
          },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Search", fontSize = 20.sp)
        }
    }
}

