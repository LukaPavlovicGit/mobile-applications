package com.example.dnevnjak.presentation.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.composable.ui.theme.primaryColor
import com.example.dnevnjak.presentation.events.ObligationEvent
import com.example.dnevnjak.presentation.events.UserEvent
import com.example.dnevnjak.presentation.viewModels.UserViewModel

@Composable
fun PasswordChangeDialog(
    viewModel: UserViewModel
){

    val passwordVisibility1 = remember { mutableStateOf(false) }
    val passwordVisibility2 = remember { mutableStateOf(false) }

    val newPassword by viewModel.newPassword.collectAsState()
    val newPasswordConfirmation by viewModel.newPasswordConfirmation.collectAsState()
    val context = LocalContext.current

    AlertDialog(
        modifier = Modifier.fillMaxHeight(0.6f).fillMaxWidth(),
        onDismissRequest = {
            viewModel.onEvent(UserEvent.HideDialog)
        },
        text = {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { viewModel.onEvent(UserEvent.SetNewPassword(it)) },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility1.value = !passwordVisibility1.value
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.password_eye),
                                contentDescription = "",
                                tint = if (passwordVisibility1.value) primaryColor else Color.Gray
                            )
                        }
                    },
                    label = { Text("Enter Password") },
                    placeholder = { Text(text = "Enter Password") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisibility1.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                )

                OutlinedTextField(
                    value = newPasswordConfirmation,
                    onValueChange = { viewModel.onEvent(UserEvent.SetNewPasswordConfirmation(it)) },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility2.value = !passwordVisibility2.value
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.password_eye),
                                contentDescription = "",
                                tint = if (passwordVisibility2.value) primaryColor else Color.Gray
                            )
                        }
                    },
                    label = { Text("Confirm Password") },
                    placeholder = { Text(text = "Confirm Password") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisibility2.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        when(newPassword == newPasswordConfirmation){
                            true -> viewModel.onEvent(UserEvent.SavePassword)
                            false -> mToast(context = context)
                        }
                    },
                    shape = CutCornerShape(25),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    border = BorderStroke(4.dp, Color.DarkGray),
                ) {
                    Text(
                        text = "Save",
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = { viewModel.onEvent(UserEvent.HideDialog) },
                    shape = CutCornerShape(25),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    border = BorderStroke(4.dp, Color.DarkGray),
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 20.sp
                    )
                }
            }
        }
    )
}


private fun mToast(context: Context){
    Toast.makeText(context, "Passwords don't match. Try again...", Toast.LENGTH_LONG).show()
}
