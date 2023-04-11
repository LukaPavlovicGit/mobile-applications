package com.example.dnevnjak.presentation.composable

import android.graphics.Color.rgb
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import com.example.dnevnjak.utilities.Priority
import com.example.dnevnjak.utilities.Utility
import com.google.accompanist.pager.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ObligationReviewPage (
    viewModel: MainViewModel
){
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val obligation = remember { viewModel.obligationState.value }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(PRIMARY_COLOR)
    ) {

        ObligationModeText(viewModel = viewModel)
        Header(viewModel = viewModel)
        ObligationDate(viewModel = viewModel)

    }
}

@Composable
private fun ObligationModeText(
    viewModel: MainViewModel
){
    val mode = remember { viewModel.obligationState.value.obligationMode }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(rgb(40, 53, 147)))
    ){
        Text(
            text = mode,
            fontSize = 26.sp,
            color = Color.White,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Composable
private fun Header(
    viewModel: MainViewModel
){
    val headerDate = remember { Utility.fullDateFormatterStr(viewModel.selectedDate.value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
    ){
        Text(
            text = headerDate,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(10.dp)
                .clickable { viewModel.onEvent(ObligationEvent.CancelObligation) }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ObligationDate(
    viewModel: MainViewModel
){
    val pagerState = rememberPagerState()
    val list = remember { listOf("High","Mid","Low") }
    val colors = remember { listOf(Color.Red, Color.Yellow, Color.Green) }

    var tabIndex by remember { mutableStateOf(0) }

    val obligationState by viewModel.obligationState.collectAsState()

    val isReview by viewModel.isReviewingObligation.collectAsState()
    val isAddingNew by viewModel.isAddingObligation.collectAsState()
    val isEditing by viewModel.isEditingObligation.collectAsState()
    val isDeleting by viewModel.isDeletingObligation.collectAsState()

    when{
        isDeleting -> DeleteObligationDialog(viewModel = viewModel)
        isAddingNew -> {}
        isEditing -> {}
        isReview->{
            tabIndex = when (obligationState.priority) {
                Priority.High -> 0
                Priority.Mid -> 1
                Priority.Low -> 2
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxHeight()
    ) {

        TabRow(
            selectedTabIndex = tabIndex,
            contentColor = Color.Black,
        ) {
            list.forEachIndexed { index, _ ->
                Tab(
                    enabled = !isReview,
                    text = { Text(text = list[index], fontSize = 20.sp) },
                    modifier = if(tabIndex != index) Modifier
                        .background(Color.LightGray)
                        .border(2.dp, Color.Black)
                    else Modifier
                        .background(colors[index])
                        .border(2.dp, Color.Black),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        tabIndex = index
                        when(index){
                            0 -> { viewModel.onEvent(ObligationEvent.SetPriority(Priority.High)) }
                            1 -> { viewModel.onEvent(ObligationEvent.SetPriority(Priority.Mid)) }
                            2 -> { viewModel.onEvent(ObligationEvent.SetPriority(Priority.Low)) }
                        }
                    }
                )
            }
        }

        Row(modifier =  Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 10.dp)){
            OutlinedTextField(
                enabled = !isReview,
                value = obligationState.title,
                onValueChange = { viewModel.onEvent(ObligationEvent.SetTitle(it)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Black,

                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,//
                label = { Text(text = "Title", fontSize = 20.sp, color = Color.White) },
                textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Black)
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            OutlinedTextField(
                enabled = !isReview,
                value = Utility.timeFormatterStr(obligationState.start),
                onValueChange = { viewModel.onEvent(ObligationEvent.SetStart(LocalDateTime.now())) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.4f),
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,//
                label = { Text(text = "From", fontSize = 18.sp, color = Color.White) },
                textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Black)
            )
            OutlinedTextField(
                enabled = !isReview,
                value = Utility.timeFormatterStr(obligationState.end),
                onValueChange = { viewModel.onEvent(ObligationEvent.SetStart(LocalDateTime.now().plusHours(1))) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.4f),
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,//
                label = { Text(text = "To", fontSize = 20.sp, color = Color.White) },
                textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Black)
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            OutlinedTextField(
                enabled = !isReview,
                value = obligationState.description,
                onValueChange = { viewModel.onEvent(ObligationEvent.SetDescription(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Black,

                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,//
                label = { Text(text = "Description", fontSize = 20.sp, color = Color.White) },
                textStyle = TextStyle.Default.copy(fontSize = 25.sp, color = Color.Black, fontWeight = FontWeight.Normal)
            )
        }
        Spacer(Modifier.weight(1f))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 35.dp)){
            Button(
                onClick = {
                      when {
                          isReview -> viewModel.onEvent(ObligationEvent.EditObligation)
                          isEditing -> viewModel.onEvent(ObligationEvent.SaveObligation)
                          isAddingNew -> viewModel.onEvent(ObligationEvent.CreateObligation)
                      }
                },
                shape = CutCornerShape(25),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                border = BorderStroke(4.dp, Color.DarkGray),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .weight(1f)
                    .padding(start = 10.dp, end = 20.dp)

            ) {
                Text(
                    text = if(isReview || isDeleting) "Edit" else if (isEditing) "Save" else "Create",
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = {
                    when {
                        isReview -> viewModel.onEvent(ObligationEvent.DeleteObligation)
                        isEditing -> viewModel.onEvent(ObligationEvent.CancelObligation)
                        isAddingNew -> viewModel.onEvent(ObligationEvent.CancelObligation)
                    }
                },
                shape = CutCornerShape(25),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                border = BorderStroke(4.dp, Color.DarkGray),
               // colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .weight(1f)
                    .padding(start = 20.dp, end = 10.dp)
            ) {
                Text(
                    text = if(isReview || isDeleting) "Delete" else if (isEditing) "Cancel" else "Cancel",
                    fontSize = 20.sp
                )
            }
        }

    }
}
