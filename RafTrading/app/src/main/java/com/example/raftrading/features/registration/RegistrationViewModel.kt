package com.example.raftrading.features.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raftrading.application.SharedPreferencesManager
import com.example.raftrading.data.UserRepository
import com.example.raftrading.dtos.UserRegisterDto
import com.example.raftrading.features.UiState
import com.example.raftrading.features.registration.passwordValidation.isPasswordValid
import com.example.raftrading.states.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPrefManager: SharedPreferencesManager
): ViewModel() {

    private val _registrationDataState = MutableStateFlow(RegistrationDataState())
    val registrationDateState = _registrationDataState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Nothing)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegistrationEvent){
        when(event){
            RegistrationEvent.Submit -> {
                _uiState.value = UiState.Processing
                viewModelScope.launch {
                   when(inputsValidation()){
                       false -> _uiState.value = UiState.Failure("Given inputs are not valid...")
                       true -> {
                           val username = _registrationDataState.value.username
                           val email = _registrationDataState.value.email
                           val password = _registrationDataState.value.password
                           userRepository.register(UserRegisterDto(username, email, password)){
                               when(it){
                                   RequestState.Processing -> _uiState.value = UiState.Processing
                                   is RequestState.Success -> _uiState.value = UiState.Success(it.message!!)
                                   is RequestState.Failure -> _uiState.value = UiState.Failure(it.error!!)
                               }
                           }
                       }
                   }
                }
            }
            RegistrationEvent.ResetUiState -> _uiState.value = UiState.Nothing
            is RegistrationEvent.SetConfirmedPassword -> _registrationDataState.value = _registrationDataState.value.copy(confirmedPassword = event.confirmedPassword)
            is RegistrationEvent.SetEmail -> _registrationDataState.value = _registrationDataState.value.copy(email = event.email)
            is RegistrationEvent.SetPassword -> _registrationDataState.value = _registrationDataState.value.copy(password = event.password)
            is RegistrationEvent.SetUsername -> _registrationDataState.value = _registrationDataState.value.copy(username = event.username)
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