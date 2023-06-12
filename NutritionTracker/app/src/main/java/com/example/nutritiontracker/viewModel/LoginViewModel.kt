package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.events.LoginEvent
import com.example.nutritiontracker.presentation.UiState
import com.example.nutritiontracker.requestState.RequestState
import com.example.nutritiontracker.states.LoginDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: AuthRepository,
    private val sharedPrefManager: SharedPreferencesManager
): ViewModel() {

    private val _loginDataState = MutableStateFlow(LoginDataState())
    val loginDataState = _loginDataState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Nothing)
    val uiState = _uiState.asStateFlow()


    init{
        //onEvent(LoginEvent.Submit)
    }

    fun onEvent(event: LoginEvent){
        when(event) {
            LoginEvent.Submit -> {
                _uiState.value = UiState.Processing
                viewModelScope.launch {
                    val email = _loginDataState.value.email
                    val password = _loginDataState.value.password
                    userRepository.login(UserLoginDto(email, password)){
                        when(it){
                            RequestState.Processing -> _uiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _uiState.value = UiState.Success(it.message!!)
                                sharedPrefManager.saveUser(it.data!!)
                            }
                            is RequestState.Failure -> _uiState.value = UiState.Failure(it.error!!)

                        }
                    }
                }
            }
            LoginEvent.ResetUiState -> _uiState.value = UiState.Nothing
            is LoginEvent.SetEmail -> _loginDataState.value = _loginDataState.value.copy(email = event.email)
            is LoginEvent.SetPassword -> _loginDataState.value = _loginDataState.value.copy(password = event.password)
        }
    }

}