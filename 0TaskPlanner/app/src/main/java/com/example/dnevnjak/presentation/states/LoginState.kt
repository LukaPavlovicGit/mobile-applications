package com.example.dnevnjak.presentation.states

data class LoginState(
    var username: String = "",
    var email: String = "",
    var password: String = "",

    var incorrectCredentials: Boolean = false,
    var incorrectPassword: Boolean = false,
    var loginSuccess: Boolean = false
)
