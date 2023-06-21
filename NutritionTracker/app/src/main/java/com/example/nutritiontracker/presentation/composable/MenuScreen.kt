package com.example.nutritiontracker.presentation.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadImageFromUrl
import com.example.nutritiontracker.presentation.composable.cammon.SearchMeals
import com.example.nutritiontracker.presentation.composable.navigation.BottomBar
import com.example.nutritiontracker.states.screens.MainScreenState
import com.example.nutritiontracker.states.screens.RemoteMenuScreenState
import com.example.nutritiontracker.viewModel.MainViewModel



@Composable
fun MenuScreen(
    viewModel: MainViewModel = viewModel(),
    menuScreenTabIdx: Int
){
    TabScreen(viewModel = viewModel, menuScreenTabIdx = menuScreenTabIdx)
}

@Composable
private fun TabScreen(
    viewModel: MainViewModel = viewModel(),
    menuScreenTabIdx: Int
) {
    val tabOptions = listOf("Local", "Remote")
    val selectedTabIndex = remember { mutableStateOf(menuScreenTabIdx) }

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
            0 -> LocalMenuScreen(viewModel = viewModel)
            1 -> RemoteMenuScreen(viewModel = viewModel)
        }
    }
}


@Composable
fun RemoteMenuScreen(
    viewModel: MainViewModel = viewModel()
){
    val energy = viewModel.menuScreenEnergyData.collectAsState()
    when(energy.value.allCategoriesModel){
        null -> { Log.e("MenuScreen", "MENU SCREEN DON'T HAVE ENERGY") }
        else -> {
            EnergizeRemoteMenuScreen(viewModel = viewModel)
        }
    }
}

@Composable
private fun EnergizeRemoteMenuScreen(
    viewModel: MainViewModel = viewModel()
){

    val remoteMenuScreenState = viewModel.remoteMenuScreenState.collectAsState()
    when(remoteMenuScreenState.value){
        RemoteMenuScreenState.Default -> DefaultRemoteMenuScreen(viewModel = viewModel)
        RemoteMenuScreenState.Error -> TODO()
    }
}

@Composable
private fun DefaultRemoteMenuScreen(
    viewModel: MainViewModel = viewModel()
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        SearchMeals(
            viewModel = viewModel,
            placeholder = "search by name",
            callBack = {
                viewModel.onEvent(MainEvent.SearchMealsByName(
                    name = it,
                    onNotFound = {
//                        viewModel.onEvent(MainEvent.SetRemoteMenuScreenState(RemoteMenuScreenState.Default))
                        viewModel.onEvent(MainEvent.SetMainScreenState(state = MainScreenState.NavigationBarScreen(startDestination = BottomBar.Menu.route, menuScreenTabIdx = 1)))
                    }))
            }
        )
        SearchMeals(
            viewModel = viewModel,
            placeholder = "search by ingredient",
            callBack = {
                viewModel.onEvent(MainEvent.SearchMealsByIngredient(
                    ingredient = it,
                    onNotFound = {
//                        viewModel.onEvent(MainEvent.SetRemoteMenuScreenState(RemoteMenuScreenState.Default))
                        viewModel.onEvent(MainEvent.SetMainScreenState(state = MainScreenState.NavigationBarScreen(startDestination = BottomBar.Menu.route, menuScreenTabIdx = 1)))
                    }))
            }
        )
        ListCategories(viewModel = viewModel)
    }
}

@Composable
private fun ListCategories(
    viewModel: MainViewModel = viewModel()
){

    val energy = viewModel.menuScreenEnergyData.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val categoryDescription = remember { mutableStateOf("") }

    LazyColumn(content = {
        items(energy.value.allCategoriesModel!!.categories.size){ idx->
            val category = energy.value.allCategoriesModel!!.categories[idx]
            Box(
                modifier = Modifier.clickable {
                    viewModel.onEvent(MainEvent.CategorySelection(category = category, onBack = {
                        viewModel.onEvent(MainEvent.SetMainScreenState(state = MainScreenState.NavigationBarScreen(startDestination = BottomBar.Menu.route, menuScreenTabIdx = 1)))
                    }))
                }
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ){
                    LoadImageFromUrl(url = category.strCategoryThumb)
                    Text(text = category.strCategory)
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Overflow Menu",
                        modifier = Modifier.clickable {
                            categoryDescription.value = category.strCategoryDescription
                            openDialog.value = true
                        }
                    )
                }
            }
        }
    })

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Category description")
            },
            text = {
                Text(text = categoryDescription.value)
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun LocalMenuScreen(
    viewModel: MainViewModel = viewModel()
){

}
