package com.example.dnevnjak.presentation.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnevnjak.data.repository.UserRepository
import com.example.dnevnjak.presentation.events.UserEvent
import com.example.dnevnjak.presentation.states.LoginState
import com.example.dnevnjak.presentation.states.PasswordChangeState
import com.example.dnevnjak.utilities.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _passwordChangeState = MutableStateFlow(PasswordChangeState())
    val passwordChangeState = _passwordChangeState.asStateFlow()

    init {
        _loginState.value = _loginState.value.copy(
            email = sharedPreferences.getString(Constants.EMAIL_KEY, "")!!,
            username = sharedPreferences.getString(Constants.USERNAME_KEY, "")!!
        )
    }

    fun onEvent(event: UserEvent){
        when(event){
            UserEvent.Login -> {
                viewModelScope.launch {
                    when( val userEntity = userRepository.getUserByUsernameAndEmail(_loginState.value.username, _loginState.value.email).first() ){
                        null -> {
                            _loginState.value = _loginState.value.copy(incorrectCredentials = true)
                            delay(2000)
                            _loginState.value = _loginState.value.copy(incorrectCredentials = false)
                        }
                        else -> {
                            when(userEntity.password == _loginState.value.password){
                                true -> {
                                    sharedPreferences.edit().apply {
                                        putString(Constants.EMAIL_KEY,  userEntity.email)
                                        putString(Constants.USERNAME_KEY, userEntity.username)
                                        apply()
                                    }
                                    _loginState.value = _loginState.value.copy(loginSuccess = true)
                                    delay(2000)
                                    _loginState.value = _loginState.value.copy(loginSuccess = false)
                                }
                                false -> {
                                    _loginState.value = _loginState.value.copy(incorrectPassword = true)
                                    delay(2000)
                                    _loginState.value = _loginState.value.copy(incorrectPassword = false)
                                }
                            }
                        }
                    }
                }
            }
            is UserEvent.SetEmail -> _loginState.value = _loginState.value.copy(email = event.email)
            is UserEvent.SetUsername -> _loginState.value = _loginState.value.copy(email = event.username)
            is UserEvent.SetPassword -> _loginState.value = _loginState.value.copy(email = event.password)
            UserEvent.Logout -> {
                _loginState.value = LoginState()
                sharedPreferences
                    .edit()
                    .clear()
                    .apply()
            }
            UserEvent.PasswordChange -> _passwordChangeState.value = _passwordChangeState.value.copy(isPasswordChanging = true)
            UserEvent.HideDialog -> _passwordChangeState.value = PasswordChangeState()
            UserEvent.SavePassword -> _passwordChangeState.value = _passwordChangeState.value.copy(isPasswordChanging = false)
            is UserEvent.SetNewPassword -> _passwordChangeState.value = _passwordChangeState.value.copy(newPassword = event.newPassword)
            is UserEvent.SetNewPasswordConfirmation -> _passwordChangeState.value = _passwordChangeState.value.copy(newPasswordConfirmation = event.newPasswordConfirmation)
        }
    }
}