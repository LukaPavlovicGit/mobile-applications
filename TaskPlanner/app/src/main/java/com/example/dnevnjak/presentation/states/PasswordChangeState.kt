package com.example.dnevnjak.presentation.states

data class PasswordChangeState(
    var newPassword: String = "",
    var newPasswordConfirmation: String = "",

    var isPasswordChanging: Boolean = false,
    var successfulPasswordChange: Boolean = false,
    var passwordNotValid: Boolean = false
)
