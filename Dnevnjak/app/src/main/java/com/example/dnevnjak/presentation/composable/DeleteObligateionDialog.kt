package com.example.dnevnjak.presentation.composable

import android.graphics.Color.rgb
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.viewModels.MainViewModel

@Composable
fun DeleteObligationDialog(
    viewModel: MainViewModel
){
    MaterialTheme {
        Column {
                AlertDialog(
                    backgroundColor = Color(rgb(55, 71, 79)),

                    onDismissRequest = {

                    },
                    title = {
                        Text(text = "Please confirm", color = Color.White)
                    },
                    text = {
                        Text("Should i continue with deletion request?", color = Color.White)
                    },

                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.onEvent(ObligationEvent.DeleteObligationConfirmed)
                            },
                            shape = CutCornerShape(25),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            border = BorderStroke(4.dp, Color.DarkGray),
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)

                        ) {
                            Text(
                                text = "OK",
                                fontSize = 20.sp
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                viewModel.onEvent(ObligationEvent.DeleteObligationCanceled)
                            },
                            shape = CutCornerShape(25),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                            border = BorderStroke(4.dp, Color.DarkGray),
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "Cancel" ,
                                fontSize = 20.sp
                            )
                        }
                    }
                )
            }
        }
}