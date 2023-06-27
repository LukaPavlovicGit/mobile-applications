package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal

data class PaginationDataState(
    val isLoading: Boolean = false,
    val items: List<Meal> = emptyList(),
    var error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
