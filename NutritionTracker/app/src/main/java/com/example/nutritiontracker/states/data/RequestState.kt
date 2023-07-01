package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.domainModels.Meal

sealed class RequestState {
    data class Error(val message: String): RequestState()
    object NotFound: RequestState()
    data class Success(val movies: List<Meal>): RequestState()
}