package com.example.raftrading.features.registration

data class RegistrationDataState(
    var username: String = "username",
    var email: String = "lukapa@gmail.com",
    var password: String = "passWord1",
    val confirmedPassword: String = "passWord1"
)
