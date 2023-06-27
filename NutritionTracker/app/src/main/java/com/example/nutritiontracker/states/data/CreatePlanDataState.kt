package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetails
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.PlanedMeal
import java.time.LocalDate

data class CreatePlanDataState(
    var from: LocalDate = LocalDate.now(),
    var to: LocalDate = LocalDate.now().plusDays(7),
    var email: String = "abcd@gmail.com",

    var currDay: Int = 0,
    var currMeal: Int = 0,
    var selectedMeal: MealDetails? = null,
    var plan: List<PlanedMeal> = mutableListOf(),
    var byDay: List<PlanedMeal> = mutableListOf()
)