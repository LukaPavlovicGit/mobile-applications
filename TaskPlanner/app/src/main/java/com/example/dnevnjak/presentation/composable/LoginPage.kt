package com.example.dnevnjak.presentation.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.viewModels.UserViewModel
import com.example.dnevnjak.presentation.composable.ui.theme.primaryColor
import com.example.dnevnjak.presentation.composable.ui.theme.WHITE
import com.example.dnevnjak.presentation.events.UserEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginPage(
    viewModel: UserViewModel = koinViewModel(),
    onSuccess: () -> Unit
) {

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val loginState by viewModel.loginState.collectAsState()

    val mContext = LocalContext.current

    when{
        loginState.incorrectCredentials -> mToastIncorrectCredentials(mContext)
        loginState.incorrectPassword -> mToastIncorrectPassword(mContext)
        loginState.loginSuccess -> {
            mToastLoginSuccess(mContext)
            onSuccess.invoke()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {

            Image(
                painterResource(R.drawable.login_image),
                contentDescription = "content description",
                modifier = Modifier.fillMaxSize(0.5f),
            )
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
                        value = loginState.email,
                        onValueChange = { viewModel.onEvent(UserEvent.SetEmail(it)) },
                        label = { Text(text = "Email Address") },
                        placeholder = { Text(text = "Email Address") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
//                        onImeActionPerformed = {
//                            focusRequester.requestFocus()
//                        }
                    )

                    OutlinedTextField(
                        value = loginState.username,
                        onValueChange = { viewModel.onEvent(UserEvent.SetUsername(it)) },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Username") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
//                        onImeActionPerformed = {
//                            focusRequester.requestFocus()
//                        }
                    )
                    OutlinedTextField(
                        value = loginState.password,
                        onValueChange = { viewModel.onEvent(UserEvent.SetPassword(it)) },
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
                        onClick = { viewModel.onEvent(UserEvent.Login) },
                        shape = CutCornerShape(25),
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

private fun mToastIncorrectCredentials(context: Context){
    Toast.makeText(context, "Incorrect credentials. Try again...", Toast.LENGTH_LONG).show()
}

private fun mToastIncorrectPassword(context: Context){
    Toast.makeText(context, "Incorrect password. Try again...", Toast.LENGTH_LONG).show()
}

private fun mToastLoginSuccess(context: Context){
    Toast.makeText(context, "Login success!", Toast.LENGTH_LONG).show()
}


