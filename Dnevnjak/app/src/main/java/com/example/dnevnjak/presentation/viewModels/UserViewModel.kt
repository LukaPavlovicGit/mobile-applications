package com.example.dnevnjak.presentation.viewModels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnevnjak.data.repository.UserRepository
import com.example.dnevnjak.presentation.events.UserEvent
import com.example.dnevnjak.utilities.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UserViewModel(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _isPasswordChanging = MutableStateFlow(false)
    val isPasswordChanging = _isPasswordChanging.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _newPasswordConfirmation = MutableStateFlow("")
    val newPasswordConfirmation = _newPasswordConfirmation.asStateFlow()

    private val _notMatchedPasswords = MutableStateFlow(false)
    val notMatchedPasswords = _notMatchedPasswords.asStateFlow()

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful = _isLoginSuccessful.asStateFlow()

    private val _incorrectCredentials = MutableStateFlow(false)
    val incorrectCredentials = _incorrectCredentials.asStateFlow()

    private val _incorrectPassword = MutableStateFlow(false)
    val incorrectPassword = _incorrectPassword.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    init{
        _email.value = sharedPreferences.getString(Constants.EMAIL_KEY, "")!!
        _username.value = sharedPreferences.getString(Constants.USERNAME_KEY, "")!!
    }

    fun onEvent(event: UserEvent){
        when(event){
            UserEvent.Login -> {
                viewModelScope.launch {
                    when( val userEntity = userRepository.getUserByUsernameAndEmail(_username.value, _email.value).first() ){
                        null -> {
                            _incorrectCredentials.value = true
                            delay(2000)
                            _incorrectCredentials.value = false
                        }
                        else -> {

                            when(userEntity.password == _password.value){
                                true -> {
                                    sharedPreferences.edit().apply {
                                        putString(Constants.EMAIL_KEY,  userEntity.email)
                                        putString(Constants.USERNAME_KEY, userEntity.username)
                                        apply()
                                    }

                                    _isLoginSuccessful.value = true
                                    delay(2000)
                                    _isLoginSuccessful.value = false
                                }
                                false -> {
                                    _incorrectPassword.value = true
                                    delay(2000)
                                    _incorrectPassword.value = false
                                }
                            }
                        }
                    }

                }
            }
            is UserEvent.SetEmail -> _email.value = event.email
            is UserEvent.SetUsername -> _username.value = event.username
            is UserEvent.SetPassword -> _password.value = event.password
            UserEvent.Logout -> {
                sharedPreferences
                    .edit()
                    .clear()
                    .apply()
            }
            UserEvent.PasswordChange -> {
                _isPasswordChanging.value = true
            }
            UserEvent.HideDialog -> {
                _isPasswordChanging.value = false
            }
            UserEvent.SavePassword -> { }
            is UserEvent.SetNewPassword -> {
                _newPassword.value = event.newPassword
            }
            is UserEvent.SetNewPasswordConfirmation -> {
                _newPasswordConfirmation.value = event.newPasswordConfirmation
            }
        }
    }


}