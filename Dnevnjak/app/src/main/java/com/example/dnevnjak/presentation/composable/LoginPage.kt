package com.example.dnevnjak.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.viewModels.LoginVIewModel
import com.example.dnevnjak.presentation.composable.ui.theme.primaryColor
import com.example.dnevnjak.presentation.composable.ui.theme.WHITE

@Composable
fun LoginPage(
    viewModel: LoginVIewModel,
    onClick: () -> Unit
) {

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize().background(Color.LightGray), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Image(painterResource(R.drawable.login_image), "content description")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(RoundedCornerShape(30.dp, 30.dp, 30.dp, 30.dp))
                .background(WHITE)
                .padding(10.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = viewModel.email.value,
                        onValueChange = { viewModel.email.value = it },
                        label = { Text(text = "Email A ddress") },
                        placeholder = { Text(text = "Email Address") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
//                        onImeActionPerformed = {
//                            focusRequester.requestFocus()
//                        }
                    )

                    OutlinedTextField(
                        value = viewModel.username.value,
                        onValueChange = { viewModel.username.value = it },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Email Address") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
//                        onImeActionPerformed = {
//                            focusRequester.requestFocus()
//                        }
                    )
                    OutlinedTextField(
                        value = viewModel.password.value,
                        onValueChange = { viewModel.password.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.password_eye),
                                    contentDescription = "",
                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
                                )
                            }
                        },
                        label = { Text("Password") },
                        placeholder = { Text(text = "Password") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester = focusRequester),
//                        onImeActionPerformed = { _, controller ->
//                            controller?.hideSoftwareKeyboard()
//                        }

                    )

                    Spacer(modifier = Modifier.padding(50.dp))
                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "Login", fontSize = 20.sp)
                    }
                }

            }

        }

    }
}


