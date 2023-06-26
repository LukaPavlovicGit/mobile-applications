package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.dtos.UserRegisterDto
import com.example.nutritiontracker.events.RegistrationEvent
import com.example.nutritiontracker.passwordValidation.isPasswordValid
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.states.data.RegistrationDataState
import com.example.nutritiontracker.states.requests.AuthRequestState
import com.example.nutritiontracker.states.screens.RegistrationScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPrefManager: SharedPreferencesManager
): ViewModel() {

    private val _registrationDataState = MutableStateFlow(RegistrationDataState())
    val registrationDateState = _registrationDataState.asStateFlow()

    private val _registrationScreenState = MutableStateFlow<RegistrationScreenState>(RegistrationScreenState.Default)
    val registrationScreenState = _registrationScreenState.asStateFlow()

    fun onEvent(event: RegistrationEvent){
        when(event){
            RegistrationEvent.Submit -> {
                _registrationScreenState.value = RegistrationScreenState.Processing
                // ZASTO NE RADI KADA SE KORISTI viewModelScope.launch
                GlobalScope.launch(Dispatchers.IO) {
                   when(inputsValidation()){
                       false -> _registrationScreenState.value = RegistrationScreenState.Failure("INVALID INPUTS")
                       true -> {
                           val username = _registrationDataState.value.username
                           val email = _registrationDataState.value.email
                           val password = _registrationDataState.value.password
                           authRepository.register(UserRegisterDto(username, email, password)){
                               when(it){
                                   is AuthRequestState.Failure -> _registrationScreenState.value = RegistrationScreenState.Failure(it.message)
                                   AuthRequestState.Success -> _registrationScreenState.value = RegistrationScreenState.Success
                               }
                           }
                       }
                   }
                }
            }
            is RegistrationEvent.SetConfirmedPassword -> _registrationDataState.value = _registrationDataState.value.copy(confirmedPassword = event.confirmedPassword)
            is RegistrationEvent.SetEmail -> _registrationDataState.value = _registrationDataState.value.copy(email = event.email)
            is RegistrationEvent.SetPassword -> _registrationDataState.value = _registrationDataState.value.copy(password = event.password)
            is RegistrationEvent.SetUsername -> _registrationDataState.value = _registrationDataState.value.copy(username = event.username)
            is RegistrationEvent.SetRegistrationScreenState -> _registrationScreenState.value = event.state
        }
    }

    private fun inputsValidation(): Boolean =
                _registrationDataState.value.email.isNotEmpty() &&
                _registrationDataState.value.username.isNotEmpty() &&
                _registrationDataState.value.password.isNotEmpty() &&
                _registrationDataState.value.confirmedPassword.isNotEmpty() &&
                _registrationDataState.value.password == _registrationDataState.value.confirmedPassword &&
                _registrationDataState.value.password.isPasswordValid

}