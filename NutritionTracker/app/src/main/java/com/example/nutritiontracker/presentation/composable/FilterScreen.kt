package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritiontracker.viewModel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.navigation.BottomBar
import com.example.nutritiontracker.states.screens.FilterScreenState
import com.example.nutritiontracker.states.screens.MainScreenState

@Composable
fun FilterScreen(
    viewModel: MainViewModel = viewModel(),
    filterScreenTabIdx: Int
){
    val menuScreenState = viewModel.filterScreenState.collectAsState()
    when(menuScreenState.value){
        FilterScreenState.Default -> TabScreen(viewModel = viewModel, filterScreenTabIdx = filterScreenTabIdx)
        FilterScreenState.Error -> TODO()
    }

}

@Composable
private fun TabScreen(
    viewModel: MainViewModel = viewModel(),
    filterScreenTabIdx: Int
) {
    val tabOptions = listOf("Categories", "Areas", "Ingredients")
    val selectedTabIndex = remember { mutableStateOf(filterScreenTabIdx) }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            tabOptions.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index }
                ) {
                    Text(text = title)
                }
            }
        }

        when (selectedTabIndex.value) {
            0 -> CategoriesTab(viewModel = viewModel)
            1 -> AreasTab(viewModel = viewModel)
            2 -> IngredientsTab(viewModel = viewModel)
        }
    }
}

@Composable
private fun CategoriesTab(
    viewModel: MainViewModel = viewModel(),
) {
    val energy = viewModel.filterScreenEnergyData.collectAsState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(energy.value.categoryNamesModel != null){
            Text(
                text = "CLICK TO SELECT AN CATEGORY",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { expanded.value = true }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                energy.value.categoryNamesModel!!.meals.forEach{ item ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        inputText.value = item.strCategory
                    }) {
                        Text(item.strCategory)
                    }
                }
            }
        }

        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            modifier = Modifier.fillMaxWidth(0.8f),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = { viewModel.onEvent(MainEvent.FilterMealsByCategory(category = inputText.value, onBack = { viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarScreen(startDestination = BottomBar.Filter.route, filterScreenTabIdx = 0))) } )) },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Filter", fontSize = 20.sp)
        }
    }
}

@Composable
private fun AreasTab(
    viewModel: MainViewModel = viewModel()
) {
    val energy = viewModel.filterScreenEnergyData.collectAsState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(energy.value.areaNamesModel != null){
            Text(
                text = "CLICK TO SELECT AN AREA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { expanded.value = true }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                energy.value.areaNamesModel!!.meals.forEach { item ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        inputText.value = item.strArea
                    }) {
                        Text(item.strArea)
                    }
                }
            }
        }

        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            modifier = Modifier.fillMaxWidth(0.8f),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = { viewModel.onEvent(MainEvent.FilterMealsByArea(area = inputText.value, onBack = { viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarScreen(startDestination = BottomBar.Filter.route, filterScreenTabIdx = 1))) } )) },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Filter", fontSize = 20.sp)
        }
    }
}

@Composable
private fun IngredientsTab(
    viewModel: MainViewModel = viewModel()
) {
    val energy = viewModel.filterScreenEnergyData.collectAsState()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(energy.value.allIngredientsNames != null){
            Text(
                text = "CLICK TO SELECT AN INGREDIENT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { expanded.value = true }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                energy.value.allIngredientsNames!!.forEach { name ->
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        inputText.value = name
                    }) {
                        Text(name)
                    }
                }
            }
        }

        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            modifier = Modifier.fillMaxWidth(0.8f),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    selectedOption.value = inputText.value
                    inputText.value = ""
                }
            )
        )
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = { viewModel.onEvent(MainEvent.FilterMealsByIngredient(ingredient = inputText.value, onBack = { viewModel.onEvent(MainEvent.SetMainScreenState(MainScreenState.NavigationBarScreen(startDestination = BottomBar.Filter.route, filterScreenTabIdx = 2))) } )) },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Text(text = "Filter", fontSize = 20.sp)
        }
    }
}

