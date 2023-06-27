package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import java.time.LocalDate

data class MealXXXXXX(

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
            1 -> strIngredient1
            2 -> strIngredient2
            3 -> strIngredient3
            4 -> strIngredient4
            5 -> strIngredient5
            6 -> strIngredient6
            7 -> strIngredient7
            8 -> strIngredient8
            9 -> strIngredient9
            10 -> strIngredient10
            11 -> strIngredient11
            12 -> strIngredient12
            13 -> strIngredient13
            14 -> strIngredient14
            15 -> strIngredient15
            16-> if(strIngredient16!=null) strIngredient16.toString() else ""
            17-> if(strIngredient17!=null) strIngredient17.toString() else ""
            18 -> if(strIngredient18!=null) strIngredient18.toString() else ""
            19 -> if(strIngredient19!=null) strIngredient19.toString() else ""
            20 -> if(strIngredient20!=null) strIngredient20.toString() else ""
            else -> ""
        }


    fun getMeasureByNum(num: Int): String =
        when (num) {
            1 -> strMeasure1
            2 -> strMeasure2
            3 -> strMeasure3
            4 -> strMeasure4
            5 -> strMeasure5
            6 -> strMeasure6
            7 -> strMeasure7
            8 -> strMeasure8
            9 -> strMeasure9
            10 -> strMeasure10
            11 -> strMeasure11
            12 -> strMeasure12
            13 -> strMeasure13
            14 -> strMeasure14
            15 -> strMeasure15
            16-> if(strMeasure16!=null) strMeasure16.toString() else ""
            17-> if(strMeasure17!=null) strMeasure17.toString() else ""
            18 -> if(strMeasure18!=null) strMeasure18.toString() else ""
            19 -> if(strMeasure19!=null) strMeasure19.toString() else ""
            20 -> if(strMeasure20!=null) strMeasure20.toString() else ""
            else -> ""
        }

}