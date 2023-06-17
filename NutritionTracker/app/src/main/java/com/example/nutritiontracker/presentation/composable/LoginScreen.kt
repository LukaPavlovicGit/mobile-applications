package com.example.nutritiontracker.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.nutritiontracker.events.LoginEvent
import com.example.nutritiontracker.presentation.composable.cammon.LoadingScreen
import com.example.nutritiontracker.presentation.composable.cammon.toast
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.viewModel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    register: () -> Unit
){
    val uiState = viewModel.uiState.collectAsState()
    when(uiState.value){
        UiState.NotFound -> LoginForm(register = register)
        UiState.Processing -> LoadingScreen()
        is UiState.Success -> {
            toast(LocalContext.current, (uiState.value as UiState.Success).message)
            viewModel.onEvent(LoginEvent.ResetUiState)
            onLoginSuccess.invoke()
        }
        is UiState.Failure -> {
            toast(LocalContext.current, (uiState.value as UiState.Failure).message)
            viewModel.onEvent(LoginEvent.ResetUiState)
        }
        UiState.Nothing -> TODO()
    }
}

@Composable
fun LoginForm(
    viewModel: LoginViewModel = viewModel(),
    register: () -> Unit
){

    val loginDataState = viewModel.loginDataState.collectAsState()

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

            TextField(
                value = loginDataState.value.email,
                onValueChange = { viewModel.onEvent(LoginEvent.SetEmail(it)) },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Email Address") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            TextField(
                value = loginDataState.value.password,
                onValueChange = { viewModel.onEvent(LoginEvent.SetPassword(it)) },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = { viewModel.onEvent(LoginEvent.Submit) },
                shape = CutCornerShape(25),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
            ) {
                Text(text = "Submit", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Button(
                onClick = { register.invoke() },
                shape = CutCornerShape(25),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
            ) {
                Text(text = "Register", fontSize = 20.sp)
            }

        }
    }
}