package com.example.dnevnjak.presentation.composable

import android.graphics.Color.rgb
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.events.UserEvent
import com.example.dnevnjak.presentation.viewModels.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfilePage(
    onLogout: () -> Unit,
    viewModel:UserViewModel = koinViewModel()
) {

    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val isPasswordChanging by viewModel.isPasswordChanging.collectAsState()

    if(isPasswordChanging){
        PasswordChangeDialog(viewModel = viewModel)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(rgb(178, 235, 242)))
            .fillMaxHeight()
    ) {

        Image(
            painterResource(R.drawable.login_image),
            contentDescription = "content description",
            modifier = Modifier
                .fillMaxSize(0.5f)
                .background(Color(rgb(178, 235, 242))),
        )
        Text(
            text = username,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = email,
            fontSize = 30.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(55.dp))
        Button(
            onClick = { viewModel.onEvent(UserEvent.PasswordChange) },
            shape = CutCornerShape(25),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(rgb(100, 181, 246))),
            border = BorderStroke(4.dp, Color.DarkGray),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(5.dp)
        ) {
            Text(text = "Change password", fontSize = 20.sp)
        }
        Button(
            onClick = { onLogout.invoke() },
            shape = CutCornerShape(25),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            border = BorderStroke(4.dp, Color.DarkGray),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(5.dp)
        ) {
            Text(text = "Log out", fontSize = 20.sp)
        }
    }
}
