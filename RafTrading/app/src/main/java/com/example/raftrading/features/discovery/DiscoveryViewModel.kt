package com.example.raftrading.features.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raftrading.data.NewsRepository
import com.example.raftrading.features.UiState
import com.example.raftrading.states.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _discoveryDataState = MutableStateFlow(DiscoveryDataState())
    val discoveryDataState = _discoveryDataState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Nothing)
    val uiState = _uiState.asStateFlow()



    init {
        onEvent(DiscoveryEvent.FetchNews)
    }

    fun onEvent(event: DiscoveryEvent){

        when(event){
            DiscoveryEvent.FetchNews -> {
                _uiState.value = UiState.Processing
                viewModelScope.launch {
                    newsRepository.fetchAll {
                        when(it){
                            is RequestState.Failure -> _uiState.value = UiState.Failure(message = it.error!!)
                            RequestState.Processing -> _uiState.value = UiState.Processing
                            is RequestState.Success -> {
                                _discoveryDataState.value = _discoveryDataState.value.copy(news = it.data!!)
                                _uiState.value = UiState.Success(message = it.message!!)
                            }
                        }
                    }
                }
            }
        }
    }
}