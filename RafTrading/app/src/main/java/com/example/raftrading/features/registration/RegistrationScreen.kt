package com.example.raftrading.features.registration

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raftrading.common.CircularIndeterminateProgressBar
import com.example.raftrading.common.toast
import com.example.raftrading.features.UiState

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    login: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        UiState.Nothing -> RegistrationForm(login = login)
        UiState.Processing -> CircularIndeterminateProgressBar()
        is UiState.Success -> {
            toast(LocalContext.current, (uiState.value as UiState.Success).message)
            viewModel.onEvent(RegistrationEvent.ResetUiState)
            onRegisterSuccess.invoke()
        }
        is UiState.Failure -> {
            toast(LocalContext.current, (uiState.value as UiState.Failure).message)
            viewModel.onEvent(RegistrationEvent.ResetUiState)
        }
    }
}

@Composable
private fun RegistrationForm(
    viewModel: RegistrationViewModel = viewModel(),
    login: () -> Unit
){

    val dataState = viewModel.registrationDateState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
                .background(Color.White)
                .padding(10.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                TextField(
                    value = dataState.value.username,
                    onValueChange = { viewModel.onEvent(RegistrationEvent.SetUsername(it)) },
                    label = { Text(text = "Username") },
                    placeholder = { Text(text = "Username") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)

                )

                TextField(
                    value = dataState.value.email,
                    onValueChange = { viewModel.onEvent(RegistrationEvent.SetEmail(it)) },
                    label = { Text(text = "Email Address") },
                    placeholder = { Text(text = "Email Address") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)

                )

                TextField(
                    value = dataState.value.password,
                    onValueChange = { viewModel.onEvent(RegistrationEvent.SetPassword(it)) },
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Password") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)

                )

                TextField(
                    value = dataState.value.confirmedPassword,
                    onValueChange = { viewModel.onEvent(RegistrationEvent.SetConfirmedPassword(it)) },
                    label = { Text(text = "Confirm Password") },
                    placeholder = { Text(text = "Confirm Password") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)

                )

                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = { viewModel.onEvent(RegistrationEvent.Submit) },
                    shape = CutCornerShape(25),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                ) {
                    Text(text = "Submit", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Button(
                    onClick = { login.invoke() },
                    shape = CutCornerShape(25),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                ) {
                    Text(text = "Login", fontSize = 20.sp)
                }
            }
        }
    }
}