package com.example.nutritiontracker.data.datasource.remote.retrofitModels

import com.example.nutritiontracker.data.datasource.local.entities.mealType.MealType
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class MealDetailsRemoteEntity(

                                                   var id: Long = -1,
                                                   var dateToEat: LocalDate = LocalDate.now(),
                                                   var mealType: MealType = MealType.Breakfast,
                                                   var saved: Boolean = false,


    @SerializedName("idMeal")                      var remoteIdMeal: String = "",
    @SerializedName("strMeal")                     var name: String = "",
    @SerializedName("strArea")                     var area: String = "",
    @SerializedName("strCategory")                 var category: String = "",
    @SerializedName("strMealThumb")                var imageUri: String = "",

    @SerializedName("strIngredient1")              var strIngredient1: String = "",
    @SerializedName("strIngredient2")              var strIngredient2: String = "",
    @SerializedName("strIngredient3")              var strIngredient3: String = "",
    @SerializedName("strIngredient4")              var strIngredient4: String = "",
    @SerializedName("strIngredient5")              var strIngredient5: String = "",
    @SerializedName("strIngredient6")              var strIngredient6: String = "",
    @SerializedName("strIngredient7")              var strIngredient7: String = "",
    @SerializedName("strIngredient8")              var strIngredient8: String = "",
    @SerializedName("strIngredient9")              var strIngredient9: String = "",
    @SerializedName("strIngredient10")             var strIngredient10: String = "",
    @SerializedName("strIngredient11")             var strIngredient11: String = "",
    @SerializedName("strIngredient12")             var strIngredient12: String = "",
    @SerializedName("strIngredient13")             var strIngredient13: String = "",
    @SerializedName("strIngredient14")             var strIngredient14: String = "",
    @SerializedName("strIngredient15")             var strIngredient15: String = "",
    @SerializedName("strIngredient16")             var strIngredient16: Any = "",
    @SerializedName("strIngredient17")             var strIngredient17: Any = "",
    @SerializedName("strIngredient18")             var strIngredient18: Any = "",
    @SerializedName("strIngredient19")             var strIngredient19: Any = "",
    @SerializedName("strIngredient20")             var strIngredient20: Any = "",

    @SerializedName("strMeasure1")                 var strMeasure1: String = "",
    @SerializedName("strMeasure2")                 var strMeasure2: String = "",
    @SerializedName("strMeasure3")                 var strMeasure3: String = "",
    @SerializedName("strMeasure4")                 var strMeasure4: String = "",
    @SerializedName("strMeasure5")                 var strMeasure5: String = "",
    @SerializedName("strMeasure6")                 var strMeasure6: String = "",
    @SerializedName("strMeasure7")                 var strMeasure7: String = "",
    @SerializedName("strMeasure8")                 var strMeasure8: String = "",
    @SerializedName("strMeasure9")                 var strMeasure9: String = "",
    @SerializedName("strMeasure10")                var strMeasure10: String = "",
    @SerializedName("strMeasure11")                var strMeasure11: String = "",
    @SerializedName("strMeasure12")                var strMeasure12: String = "",
    @SerializedName("strMeasure13")                var strMeasure13: String = "",
    @SerializedName("strMeasure14")                var strMeasure14: String = "",
    @SerializedName("strMeasure15")                var strMeasure15: String = "",
    @SerializedName("strMeasure16")                var strMeasure16: Any = "",
    @SerializedName("strMeasure17")                var strMeasure17: Any = "",
    @SerializedName("strMeasure18")                var strMeasure18: Any = "",
    @SerializedName("strMeasure19")                var strMeasure19: Any = "",
    @SerializedName("strMeasure20")                var strMeasure20: Any = "",

    @SerializedName("strTags")                     var strTags: String = "",
    @SerializedName("strYoutube")                  var strYoutube: String = "",

    // unused attributes
    @SerializedName("dateModified")                var dateModified: Any = "",
    @SerializedName("strCreativeCommonsConfirmed") var strCreativeCommonsConfirmed: Any = "",
    @SerializedName("strDrinkAlternate")           var strDrinkAlternate: Any = "",
    @SerializedName("strImageSource")              var strImageSource: Any = "",
    @SerializedName("strSource")                   var strSource: Any = "",
    @SerializedName("strInstructions")             var strInstructions: String = ""

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