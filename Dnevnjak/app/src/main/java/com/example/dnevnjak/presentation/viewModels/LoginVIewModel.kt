package com.example.dnevnjak.presentation.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginVIewModel : ViewModel() {
    val email = mutableStateOf("")
    val username = mutableStateOf("")
    val password = mutableStateOf("")


    fun onEmailChanged(email: String){
        this.email.value = email
    }
    fun onUsernameChanged(username: String){
        this.username.value = username
    }
    fun onPasswordChanged(password: String){
        this.password.value = password
    }
}