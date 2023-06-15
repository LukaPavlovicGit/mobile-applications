package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.events.LoginEvent
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.states.requests.RequestState
import com.example.nutritiontracker.states.data.LoginDataState
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

    private val _uiState = MutableStateFlow<UiState>(UiState.NotFound)
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
                            is RequestState.Processing -> _uiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _uiState.value = UiState.Success(it.message!!)
                                sharedPrefManager.saveUser(it.data!!)
                            }
                            is RequestState.Failure -> _uiState.value = UiState.Failure(it.error!!)
                            is RequestState.NotFound -> TODO()
                        }
                    }
                }
            }
            LoginEvent.ResetUiState -> _uiState.value = UiState.NotFound
            is LoginEvent.SetEmail -> _loginDataState.value = _loginDataState.value.copy(email = event.email)
            is LoginEvent.SetPassword -> _loginDataState.value = _loginDataState.value.copy(password = event.password)
        }
    }

}