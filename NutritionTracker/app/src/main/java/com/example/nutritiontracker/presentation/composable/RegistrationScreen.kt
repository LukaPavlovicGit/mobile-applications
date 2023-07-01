package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutritiontracker.events.RegistrationEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadingScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.screens.RegistrationScreenState
import com.example.nutritiontracker.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    login: () -> Unit
) {

    val registrationScreenStateState = viewModel.registrationScreenState.collectAsState()
    when (registrationScreenStateState.value) {
        RegistrationScreenState.Default -> RegistrationForm(login = login)
        RegistrationScreenState.Processing -> LoadingScreen()
        RegistrationScreenState.Success -> onRegisterSuccess.invoke()
        is RegistrationScreenState.Failure -> {
            toast(LocalContext.current, (registrationScreenStateState.value as RegistrationScreenState.Failure).message!!)
            viewModel.onEvent(RegistrationEvent.SetRegistrationScreenState(RegistrationScreenState.Default))
        }
    }
}

@Composable
private fun RegistrationForm(
    viewModel: RegistrationViewModel = viewModel(),
    login: () -> Unit
){

    val dataState = viewModel.registrationDateState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.60f)
            .background(Color.White)
            .padding(10.dp)
    ) {

        TextField(
            value = dataState.value.username,
            onValueChange = { viewModel.onEvent(RegistrationEvent.SetUsername(it)) },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Username") },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f).padding(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )

        TextField(
            value = dataState.value.email,
            onValueChange = { viewModel.onEvent(RegistrationEvent.SetEmail(it)) },
            label = { Text(text = "Email Address") },
            placeholder = { Text(text = "Email Address") },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f).padding(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )

        TextField(
            value = dataState.value.password,
            onValueChange = { viewModel.onEvent(RegistrationEvent.SetPassword(it)) },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Password") },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f).padding(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )

        TextField(
            value = dataState.value.confirmedPassword,
            onValueChange = { viewModel.onEvent(RegistrationEvent.SetConfirmedPassword(it)) },
            label = { Text(text = "Confirm Password") },
            placeholder = { Text(text = "Confirm Password") },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f).padding(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )

        Spacer(modifier = Modifier.padding(20.dp))
        Button(
            onClick = { viewModel.onEvent(RegistrationEvent.Submit) },
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)
        ) {
            Text(text = "Submit", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = { login.invoke() },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)

        ) {
            Text(text = "Login", fontSize = 20.sp)
        }
    }
}