package com.example.dnevnjak.presentation.composable

import android.content.Context
import android.graphics.Color.rgb
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.composable.ui.theme.PRIMARY_COLOR
import com.example.dnevnjak.presentation.events.DnevnjakEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel
import com.example.dnevnjak.utilities.Priority
import com.example.dnevnjak.utilities.Utility
import com.google.accompanist.pager.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ObligationReviewPage (
    viewModel: MainViewModel
){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(PRIMARY_COLOR)
    ) {

        ObligationModeText(viewModel = viewModel)
        Header(viewModel = viewModel)
        ObligationData(viewModel = viewModel)

    }
}

@Composable
private fun ObligationModeText(
    viewModel: MainViewModel
){
    val obligationState by viewModel.obligationState.collectAsState()

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(rgb(40, 53, 147)))
    ){
        Text(
            text = obligationState.obligationMode,
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
    val obligationState by viewModel.obligationState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
    ){
        Text(
            text = Utility.fullDateFormatterStr(obligationState.date),
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(10.dp)
                .clickable { viewModel.onEvent(DnevnjakEvent.CancelObligation) }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ObligationData(
    viewModel: MainViewModel
){

    val obligationState by viewModel.obligationState.collectAsState()
    val pagerState = rememberPagerState()
    val list = remember { listOf("High","Mid","Low") }
    val colors = remember { listOf(Color.Red, Color.Yellow, Color.Green) }
    var tabIndex by remember { mutableStateOf(0) }

    var from by remember { mutableStateOf(obligationState.start) }
    var to by remember { mutableStateOf(obligationState.end) }

    val formattedTimeFrom by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(from)
        }
    }
    val formattedTimeTo by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(to)
        }
    }

    val timeDialogState1 = rememberMaterialDialogState()
    val timeDialogState2 = rememberMaterialDialogState()

    val mContext = LocalContext.current

    when{
        obligationState.isDeleting -> DeleteObligationDialog(viewModel = viewModel)
        obligationState.isAdding -> {}
        obligationState.isEditing -> {}
        obligationState.isReviewing -> {
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
                    enabled = !obligationState.isReviewing,
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
                            0 -> { viewModel.onEvent(DnevnjakEvent.SetPriority(Priority.High)) }
                            1 -> { viewModel.onEvent(DnevnjakEvent.SetPriority(Priority.Mid)) }
                            2 -> { viewModel.onEvent(DnevnjakEvent.SetPriority(Priority.Low)) }
                        }
                    }
                )
            }
        }

        Row(modifier =  Modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 10.dp)){
            OutlinedTextField(
                enabled = !obligationState.isReviewing,
                value = obligationState.title,
                onValueChange = { viewModel.onEvent(DnevnjakEvent.SetTitle(it)) },
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

        Row(
            modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
        ){
            OutlinedTextField(
                enabled = false,
                value = formattedTimeFrom,
                onValueChange = {  },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
                    .clickable {
                        when {
                            obligationState.isReviewing -> {}
                            else -> {
                                timeDialogState1.show()
                            }
                        }
                    },
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
                enabled = false,
                value = formattedTimeTo,
                onValueChange = {  },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
                    .clickable {
                        when {
                            obligationState.isReviewing -> {}
                            else -> {
                                timeDialogState2.show()
                            }
                        }
                    },
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

            MaterialDialog(
                dialogState = timeDialogState1,
                buttons = {
                    positiveButton(text = "Ok") { mToast(mContext) }
                    negativeButton(text = "Cancel")
                }
            ) {
                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Pick a date",
                    timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
                ) {
                    viewModel.onEvent(DnevnjakEvent.SetStart(it))
                    from = it
                }
            }
            MaterialDialog(
                dialogState = timeDialogState2,
                buttons = {
                    positiveButton(text = "Ok") { mToast(mContext) }
                    negativeButton(text = "Cancel")
                }
            ) {
                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Pick a time",
                    timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
                ) {
                    viewModel.onEvent(DnevnjakEvent.SetEnd(it))
                    to = it
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ){
            OutlinedTextField(
                enabled = !obligationState.isReviewing,
                value = obligationState.description,
                onValueChange = { viewModel.onEvent(DnevnjakEvent.SetDescription(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Black,

                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
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
                        obligationState.isReviewing -> viewModel.onEvent(DnevnjakEvent.EditObligation)
                        obligationState.isEditing -> viewModel.onEvent(DnevnjakEvent.SaveObligation)
                        obligationState.isAdding -> viewModel.onEvent(DnevnjakEvent.CreateObligation)
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
                    text = if(obligationState.isReviewing || obligationState.isDeleting)
                                "Edit"
                            else if (obligationState.isEditing)
                                "Save"
                            else
                                "Create",
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = {
                    when {
                        obligationState.isReviewing -> viewModel.onEvent(DnevnjakEvent.DeleteObligation)
                        obligationState.isEditing -> viewModel.onEvent(DnevnjakEvent.CancelObligation)
                        obligationState.isAdding -> viewModel.onEvent(DnevnjakEvent.CancelObligation)
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
                    text = if(obligationState.isReviewing || obligationState.isDeleting)
                                "Delete"
                            else if (obligationState.isEditing)
                                "Cancel"
                            else
                                "Cancel",
                    fontSize = 20.sp
                )
            }
        }

    }
}

// Function to generate a Toast
private fun mToast(context: Context){
    Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
}