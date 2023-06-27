package com.example.nutritiontracker.utils

import com.example.nutritiontracker.data.datasource.local.entities.MealEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.Meal
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetails

class Mapper {


    companion object {
        fun mealToMealEntity(meal: MealDetails): MealEntity =
            MealEntity(
                idMeal = meal.idMeal,
                strArea = meal.strArea,
                strCategory = meal.strCategory,
                strIngredient1 = meal.strIngredient1,
                strIngredient10 = if(meal.strIngredient10!=null) meal.strIngredient10 else "",
                strIngredient11 = meal.strIngredient11,
                strIngredient12 = meal.strIngredient12,
                strIngredient13 = meal.strIngredient13,
                strIngredient14 = meal.strIngredient14,
                strIngredient15 = meal.strIngredient15,
                strIngredient16 = if(meal.strIngredient16!=null) meal.strIngredient16.toString() else "",
                strIngredient17 = if(meal.strIngredient17!=null) meal.strIngredient17.toString() else "",
                strIngredient18 = if(meal.strIngredient18!=null) meal.strIngredient18.toString() else "",
                strIngredient19 = if(meal.strIngredient19!=null) meal.strIngredient19.toString() else "",
                strIngredient2 = meal.strIngredient2,
                strIngredient20 = if(meal.strIngredient20!=null) meal.strIngredient20.toString() else "",
                strIngredient4 = meal.strIngredient4,
                strIngredient5 = meal.strIngredient5,
                strIngredient6 = meal.strIngredient6,
                strIngredient7 = meal.strIngredient7,
                strIngredient8 = meal.strIngredient8,
                strIngredient9 = meal.strIngredient9,
                strInstructions = meal.strInstructions,
                strMeal = meal.strMeal,
                strMealThumb = meal.strMealThumb,
                strMeasure1 = meal.strMeasure1,
                strMeasure10 = meal.strMeasure10,
                strMeasure11 = meal.strMeasure11,
                strMeasure12 = meal.strMeasure12,
                strMeasure13 = meal.strMeasure13,
                strMeasure14 = meal.strMeasure14,
                strMeasure15 = meal.strMeasure15,
                strMeasure16 = if(meal.strMeasure16!=null) meal.strMeasure16.toString() else "",
                strMeasure17 = if(meal.strMeasure17!=null) meal.strMeasure17.toString() else "",
                strMeasure18 = if(meal.strMeasure18!=null) meal.strMeasure18.toString() else "",
                strMeasure19 = if(meal.strMeasure19!=null) meal.strMeasure19.toString() else "",
                strMeasure2 = meal.strMeasure2,
                strMeasure20 = if(meal.strMeasure20!=null) meal.strMeasure20.toString() else "",
                strMeasure3 = meal.strMeasure3,
                strMeasure4 = meal.strMeasure4,
                strMeasure5 = meal.strMeasure5,
                strMeasure6 = meal.strMeasure6,
                strMeasure7 = meal.strMeasure7,
                strMeasure8 = meal.strMeasure8,
                strMeasure9 = meal.strMeasure9,
                strTags = if(meal.strTags!=null) meal.strTags else "",
                strYoutube = meal.strYoutube
            )

        fun mealEntityToMealDetails(mealEntity: MealEntity): MealDetails =
            MealDetails(

                id = mealEntity.id,
                dateToEat = mealEntity.dateToEat,
                mealType = mealEntity.mealType,
                saved = true,

                idMeal = mealEntity.idMeal,
                strArea = mealEntity.strArea,
                strCategory = mealEntity.strCategory,
                strIngredient1 = mealEntity.strIngredient1,
                strIngredient10 = mealEntity.strIngredient10,
                strIngredient11 = mealEntity.strIngredient11,
                strIngredient12 = mealEntity.strIngredient12,
                strIngredient13 = mealEntity.strIngredient13,
                strIngredient14 = mealEntity.strIngredient14,
                strIngredient15 = mealEntity.strIngredient15,
                strIngredient16 = mealEntity.strIngredient16,
                strIngredient17 = mealEntity.strIngredient17,
                strIngredient18 = mealEntity.strIngredient18,
                strIngredient19 = mealEntity.strIngredient19,
                strIngredient2 = mealEntity.strIngredient2,
                strIngredient20 = mealEntity.strIngredient20,
                strIngredient3 = mealEntity.strIngredient3,
                strIngredient4 = mealEntity.strIngredient4,
                strIngredient5 = mealEntity.strIngredient5,
                strIngredient6 = mealEntity.strIngredient6,
                strIngredient7 = mealEntity.strIngredient7,
                strIngredient8 = mealEntity.strIngredient8,
                strIngredient9 = mealEntity.strIngredient9,
                strInstructions = mealEntity.strInstructions,
                strMeal = mealEntity.strMeal,
                strMealThumb = mealEntity.strMealThumb,
                strMeasure1 = mealEntity.strMeasure1,
                strMeasure10 = mealEntity.strMeasure10,
                strMeasure11 = mealEntity.strMeasure11,
                strMeasure12 = mealEntity.strMeasure12,
                strMeasure13 = mealEntity.strMeasure13,
                strMeasure14 = mealEntity.strMeasure14,
                strMeasure15 = mealEntity.strMeasure15,
                strMeasure16 = mealEntity.strMeasure16,
                strMeasure17 = mealEntity.strMeasure17,
                strMeasure18 = mealEntity.strMeasure18,
                strMeasure19 = mealEntity.strMeasure19,
                strMeasure2 = mealEntity.strMeasure2,
                strMeasure20 = mealEntity.strMeasure20,
                strMeasure3 = mealEntity.strMeasure3,
                strMeasure4 = mealEntity.strMeasure4,
                strMeasure5 = mealEntity.strMeasure5,
                strMeasure6 = mealEntity.strMeasure6,
                strMeasure7 = mealEntity.strMeasure7,
                strMeasure8 = mealEntity.strMeasure8,
                strMeasure9 = mealEntity.strMeasure9,
                strTags = mealEntity.strTags,
                strYoutube = mealEntity.strYoutube,

                dateModified = "",
                strCreativeCommonsConfirmed = "",
                strDrinkAlternate = "",
                strImageSource = "",
                strSource = ""
            )

        fun mealEntityToMeal(mealEntity: MealEntity): Meal =
            Meal(
                idMeal = mealEntity.idMeal,
                strMeal = mealEntity.strMeal,
                strMealThumb = mealEntity.strMealThumb
            )

    }

}