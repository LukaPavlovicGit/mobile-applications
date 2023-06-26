package com.example.nutritiontracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.application.SharedPreferencesManager
import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.events.LoginEvent
import com.example.nutritiontracker.states.UiState
import com.example.nutritiontracker.states.requests.RetrofitRequestState
import com.example.nutritiontracker.states.data.LoginDataState
import com.example.nutritiontracker.states.requests.AuthRequestState
import com.example.nutritiontracker.states.screens.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    private val _loginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState.Default)
    val loginScreenState = _loginScreenState.asStateFlow()


    fun onEvent(event: LoginEvent){
        when(event) {
            LoginEvent.Submit -> {
                _loginScreenState.value = LoginScreenState.Processing
                // ZASTO NE RADI KADA SE KORISTI viewModelScope.launch
                GlobalScope.launch(Dispatchers.IO) {
                    val email = _loginDataState.value.email
                    val password = _loginDataState.value.password
                    userRepository.login(UserLoginDto(email, password)){
                        when(it){
                            is AuthRequestState.Failure -> _loginScreenState.value = LoginScreenState.Failure(it.message)
                            AuthRequestState.Success -> {
                                _loginScreenState.value = LoginScreenState.Success
                                sharedPrefManager.saveUser(email)
                            }
                        }
                    }
                }
            }
            is LoginEvent.SetEmail -> _loginDataState.value = _loginDataState.value.copy(email = event.email)
            is LoginEvent.SetPassword -> _loginDataState.value = _loginDataState.value.copy(password = event.password)
            is LoginEvent.SetLoginScreenState -> _loginScreenState.value = event.state
        }
    }

}