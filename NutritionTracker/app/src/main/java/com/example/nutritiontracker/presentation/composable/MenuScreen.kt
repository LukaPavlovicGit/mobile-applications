package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
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
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.viewModel.MainViewModel

@Composable
fun MenuScreen(
    viewModel: MainViewModel = viewModel()
){

    val uiState = viewModel.mainUiState.collectAsState()
    when(uiState.value){
        is UiState.Failure -> TODO()
        UiState.Nothing -> {  }
        UiState.Processing -> LoadingScreen()
        is UiState.Success -> CategoriesList(viewModel = viewModel)
    }
}

@Composable
private fun CategoriesList(
    viewModel: MainViewModel = viewModel()
){
    val mainDataState = viewModel.mainDataState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val categoryDescription = remember { mutableStateOf("") }

    LazyColumn(content = {
        items(mainDataState.value.allCategoriesModel!!.categories.size){ idx->
            val category = mainDataState.value.allCategoriesModel!!.categories[idx]
            Box(
               modifier = Modifier.clickable { viewModel.onEvent(MainEvent.CategorySelection(category)) }
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

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
