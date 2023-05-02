package com.example.dnevnjak.presentation.events

sealed interface UserEvent{
    data class SetEmail(val email: String): UserEvent
    data class SetUsername(val username: String): UserEvent
    data class SetPassword(val password: String): UserEvent
    object Login: UserEvent
    object Logout: UserEvent
    object PasswordChange: UserEvent
    object SavePassword: UserEvent
    object HideDialog: UserEvent
    data class SetNewPassword(val newPassword: String): UserEvent
    data class SetNewPasswordConfirmation(val newPasswordConfirmation: String): UserEvent
}