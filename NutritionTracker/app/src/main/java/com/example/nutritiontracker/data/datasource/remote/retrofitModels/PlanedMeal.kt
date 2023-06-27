package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import java.time.LocalDate

data class PlanedMeal(

    var day: Int = -1,
    var mealNum: Int = -1,

    var id: Long = -1,
    var dateToEat: LocalDate = LocalDate.now(),
    var mealType: MealType = MealType.Breakfast,
    var saved: Boolean = false,


    var dateModified: Any = "",
    var idMeal: String = "",
    var strArea: String = "",
    var strCategory: String = "",
    var strCreativeCommonsConfirmed: Any = "",
    var strDrinkAlternate: Any = "",
    var strImageSource: Any = "",
    var strIngredient1: String = "",
    var strIngredient10: String = "",
    var strIngredient11: String = "",
    var strIngredient12: String = "",
    var strIngredient13: String = "",
    var strIngredient14: String = "",
    var strIngredient15: String = "",
    var strIngredient16: Any = "",
    var strIngredient17: Any = "",
    var strIngredient18: Any = "",
    var strIngredient19: Any = "",
    var strIngredient2: String = "",
    var strIngredient20: Any = "",
    var strIngredient3: String = "",
    var strIngredient4: String = "",
    var strIngredient5: String = "",
    var strIngredient6: String = "",
    var strIngredient7: String = "",
    var strIngredient8: String = "",
    var strIngredient9: String = "",
    var strInstructions: String = "",
    var strMeal: String = "",
    var strMealThumb: String = "",
    var strMeasure1: String = "",
    var strMeasure10: String = "",
    var strMeasure11: String = "",
    var strMeasure12: String = "",
    var strMeasure13: String = "",
    var strMeasure14: String = "",
    var strMeasure15: String = "",
    var strMeasure16: Any = "",
    var strMeasure17: Any = "",
    var strMeasure18: Any = "",
    var strMeasure19: Any = "",
    var strMeasure2: String = "",
    var strMeasure20: Any = "",
    var strMeasure3: String = "",
    var strMeasure4: String = "",
    var strMeasure5: String = "",
    var strMeasure6: String = "",
    var strMeasure7: String = "",
    var strMeasure8: String = "",
    var strMeasure9: String = "",
    var strSource: Any = "",
    var strTags: String = "",
    var strYoutube: String = ""
){

    fun getIngredientByNum(num: Int): String =
        when (num) {
            1 -> safeStr(strIngredient1)
            2 -> safeStr(strIngredient2)
            3 -> safeStr(strIngredient3)
            4 -> safeStr(strIngredient4)
            5 -> safeStr(strIngredient5)
            6 -> safeStr(strIngredient6)
            7 -> safeStr(strIngredient7)
            8 -> safeStr(strIngredient8)
            9 -> safeStr(strIngredient9)
            10 -> safeStr(strIngredient10)
            11 -> safeStr(strIngredient11)
            12 -> safeStr(strIngredient12)
            13 -> safeStr(strIngredient13)
            14 -> safeStr(strIngredient14)
            15 -> safeStr(strIngredient15)
            16-> safeStr(strIngredient16)
            17-> safeStr(strIngredient17)
            18 -> safeStr(strIngredient18)
            19 -> safeStr(strIngredient19)
            20 -> safeStr(strIngredient20)
            else -> ""
        }


    fun getMeasureByNum(num: Int): String =
        when (num) {
            1 -> safeStr(strMeasure1)
            2 -> safeStr(strMeasure2)
            3 -> safeStr(strMeasure3)
            4 -> safeStr(strMeasure4)
            5 -> safeStr(strMeasure5)
            6 -> safeStr(strMeasure6)
            7 -> safeStr(strMeasure7)
            8 -> safeStr(strMeasure8)
            9 -> safeStr(strMeasure9)
            10 -> safeStr(strMeasure10)
            11 -> safeStr(strMeasure11)
            12 -> safeStr(strMeasure12)
            13 -> safeStr(strMeasure13)
            14 -> safeStr(strMeasure14)
            15 -> safeStr(strMeasure15)
            16-> safeStr(strMeasure16)
            17-> safeStr(strMeasure17)
            18 -> safeStr(strMeasure18)
            19 -> safeStr(strMeasure19)
            20 -> safeStr(strMeasure20)
            else -> ""
        }

    private fun safeStr(str: Any?): String = str?.toString() ?: ""

}