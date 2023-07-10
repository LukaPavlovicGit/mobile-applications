package com.example.nutritiontracker.events

import java.time.LocalDate

sealed interface CreatePlanEvent{
    data class SearchMealsByCategory(val category: String): CreatePlanEvent
    data class SetStartDate(val from: LocalDate): CreatePlanEvent
    data class SetEndDate(val to: LocalDate): CreatePlanEvent
    data class SetEmailReceiver(val email: String): CreatePlanEvent
    object SetNextDay: CreatePlanEvent
    object SetPreviousDay: CreatePlanEvent
    data class InsertToPlanFromLocal(val remoteIdMeal: String): CreatePlanEvent
    data class InsertToPlanFromRemote(val remoteIdMeal: String): CreatePlanEvent
    object LoadMealsByDay: CreatePlanEvent
    data class DeletePlannedMeal(val mealNum: Int): CreatePlanEvent
}