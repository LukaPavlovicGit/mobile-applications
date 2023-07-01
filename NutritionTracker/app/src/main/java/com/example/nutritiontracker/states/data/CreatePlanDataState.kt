package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetailsRemoteEntity
import com.example.nutritiontracker.domainModels.PlanedMeal
import java.time.LocalDate

data class CreatePlanDataState(
    var from: LocalDate = LocalDate.now(),
    var to: LocalDate = LocalDate.now().plusDays(7),
    var email: String = "lukapavlovic032@gmail.com",

    var currDay: Int = 0,
    var currMeal: Int = 0,
    var selectedMeal: MealDetailsRemoteEntity? = null,
    var plan: List<PlanedMeal> = mutableListOf(),
    var byDay: List<PlanedMeal> = mutableListOf()
){

    fun emailBody(): String {

        val map = hashMapOf<Int, String>()

        for(meal in plan){
            if(map[meal.day] == null) map[meal.day] = meal.toString()
            else map[meal.day] += "\n" + meal.toString()
        }

        val stringBuilder = StringBuilder()
        for ((day, meals) in map) {
            stringBuilder.append("day $day:\n")
            stringBuilder.append(meals)
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }

}