package com.example.dnevnjak.presentation.viewModels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnevnjak.data.repository.UserRepository
import com.example.dnevnjak.presentation.events.UserEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
): ViewModel() {

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

    fun onEvent(event: UserEvent){
        when(event){
            UserEvent.Login -> {
                viewModelScope.launch {
                    val userEntity  = userRepository.getUserByUsernameAndEmail(_username.value, _email.value).first()

                    when(userEntity){
                        null -> {
                            _incorrectCredentials.value = true
                            delay(2000)
                            _incorrectCredentials.value = false
                        }
                        else -> {

                            when(userEntity.password == _password.value){
                                true -> {
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
        }
    }


}