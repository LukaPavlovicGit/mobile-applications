package com.example.nutritiontracker.states.data

import com.example.nutritiontracker.domainModels.MealDetails
import com.example.nutritiontracker.domainModels.PlannedMeal

data class CreatePlanDataState(
    var currDay: Int = 0,
    var currMeal: Int = 0,
    var selectedMeal: MealDetails? = null,
    var plan: List<PlannedMeal> = mutableListOf(),
    var byDay: List<PlannedMeal> = mutableListOf()
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
            stringBuilder.append("\n\n")
        }

        return stringBuilder.toString()
    }

}