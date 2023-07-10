package com.example.nutritiontracker.utils

import com.example.nutritiontracker.data.datasource.local.entities.MealDetailsLocalEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.CategoryRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealRemoteEntity
import com.example.nutritiontracker.data.datasource.remote.retrofitModels.MealDetailsRemoteEntity
import com.example.nutritiontracker.domainModels.Category
import com.example.nutritiontracker.domainModels.Meal
import com.example.nutritiontracker.domainModels.MealDetails
import com.example.nutritiontracker.domainModels.PlannedMeal

class Mapper {


    companion object {

        fun mealDetailsToMealDetailsLocalEntity(meal: MealDetails): MealDetailsLocalEntity =
            MealDetailsLocalEntity(
                name = safeStr(meal.name),
                remoteIdMeal = safeStr(meal.remoteIdMeal),
                area = safeStr(meal.area),
                tags = safeStr(meal.strTags),
                imageUri = safeStr(meal.imageUri),
                videoLink = safeStr(meal.strYoutube),
                category = safeStr(meal.category),
                mealType = meal.mealType,
                dateToEat = meal.dateToEat,

                strIngredient1 = safeStr(meal.strIngredient1),
                strIngredient2 = safeStr(meal.strIngredient2),
                strIngredient3 = safeStr(meal.strIngredient3),
                strIngredient4 = safeStr(meal.strIngredient4),
                strIngredient5 = safeStr(meal.strIngredient5),
                strIngredient6 = safeStr(meal.strIngredient6),
                strIngredient7 = safeStr(meal.strIngredient7),
                strIngredient8 = safeStr(meal.strIngredient8),
                strIngredient9 = safeStr(meal.strIngredient9),
                strIngredient10 = safeStr(meal.strIngredient10),
                strIngredient11 = safeStr(meal.strIngredient11),
                strIngredient12 = safeStr(meal.strIngredient12),
                strIngredient13 = safeStr(meal.strIngredient13),
                strIngredient14 = safeStr(meal.strIngredient14),
                strIngredient15 = safeStr(meal.strIngredient15),
                strIngredient16 = safeStr(meal.strIngredient16),
                strIngredient17 = safeStr(meal.strIngredient17),
                strIngredient18 = safeStr(meal.strIngredient18),
                strIngredient19 = safeStr(meal.strIngredient19),
                strIngredient20 = safeStr(meal.strIngredient20),

                strMeasure1 = safeStr(meal.strMeasure1),
                strMeasure2 = safeStr(meal.strMeasure2),
                strMeasure3 = safeStr(meal.strMeasure3),
                strMeasure4 = safeStr(meal.strMeasure4),
                strMeasure5 = safeStr(meal.strMeasure5),
                strMeasure6 = safeStr(meal.strMeasure6),
                strMeasure7 = safeStr(meal.strMeasure7),
                strMeasure8 = safeStr(meal.strMeasure8),
                strMeasure9 = safeStr(meal.strMeasure9),
                strMeasure10 = safeStr(meal.strMeasure10),
                strMeasure11 = safeStr(meal.strMeasure11),
                strMeasure12 = safeStr(meal.strMeasure12),
                strMeasure13 = safeStr(meal.strMeasure13),
                strMeasure14 = safeStr(meal.strMeasure14),
                strMeasure15 = safeStr(meal.strMeasure15),
                strMeasure16 = safeStr(meal.strMeasure16),
                strMeasure17 = safeStr(meal.strMeasure17),
                strMeasure18 = safeStr(meal.strMeasure18),
                strMeasure19 = safeStr(meal.strMeasure19),
                strMeasure20 = safeStr(meal.strMeasure20),

                strInstructions = safeStr(meal.strInstructions)
            )

        fun mealToMealEntity(meal: MealDetailsRemoteEntity): MealDetailsLocalEntity =
            MealDetailsLocalEntity(
                remoteIdMeal = safeStr(meal.remoteIdMeal),
                area = safeStr(meal.area),
                category = safeStr(meal.category),
                strIngredient1 = safeStr(meal.strIngredient1),
                strIngredient10 = safeStr(meal.strIngredient10),
                strIngredient11 = safeStr(meal.strIngredient11),
                strIngredient12 = safeStr(meal.strIngredient12),
                strIngredient13 = safeStr(meal.strIngredient13),
                strIngredient14 = safeStr(meal.strIngredient14),
                strIngredient15 = safeStr(meal.strIngredient15),
                strIngredient16 = safeStr(meal.strIngredient16),
                strIngredient17 = safeStr(meal.strIngredient17),
                strIngredient18 = safeStr(meal.strIngredient18),
                strIngredient19 = safeStr(meal.strIngredient19),
                strIngredient2 = safeStr(meal.strIngredient2),
                strIngredient20 = safeStr(meal.strIngredient20),
                strIngredient4 = safeStr(meal.strIngredient4),
                strIngredient5 = safeStr(meal.strIngredient5),
                strIngredient6 = safeStr(meal.strIngredient6),
                strIngredient7 = safeStr(meal.strIngredient7),
                strIngredient8 = safeStr(meal.strIngredient8),
                strIngredient9 = safeStr(meal.strIngredient9),
                strInstructions = safeStr(meal.strInstructions),
                name = safeStr(meal.name),
                imageUri = safeStr(meal.imageUri),
                strMeasure1 = safeStr(meal.strMeasure1),
                strMeasure10 = safeStr(meal.strMeasure10),
                strMeasure11 = safeStr(meal.strMeasure11),
                strMeasure12 = safeStr(meal.strMeasure12),
                strMeasure13 = safeStr(meal.strMeasure13),
                strMeasure14 = safeStr(meal.strMeasure14),
                strMeasure15 = safeStr(meal.strMeasure15),
                strMeasure16 = safeStr(meal.strMeasure16),
                strMeasure17 = safeStr(meal.strMeasure17),
                strMeasure18 = safeStr(meal.strMeasure18),
                strMeasure19 = safeStr(meal.strMeasure19),
                strMeasure2 = safeStr(meal.strMeasure2),
                strMeasure20 = safeStr(meal.strMeasure20),
                strMeasure3 = safeStr(meal.strMeasure3),
                strMeasure4 = safeStr(meal.strMeasure4),
                strMeasure5 = safeStr(meal.strMeasure5),
                strMeasure6 = safeStr(meal.strMeasure6),
                strMeasure7 = safeStr(meal.strMeasure7),
                strMeasure8 = safeStr(meal.strMeasure8),
                strMeasure9 = safeStr(meal.strMeasure9),
                tags = safeStr(meal.strTags),
                videoLink = safeStr(meal.strYoutube)
            )

        fun mealEntityToMealDetails(meal: MealDetailsLocalEntity): MealDetailsRemoteEntity =
            MealDetailsRemoteEntity(
                remoteIdMeal = safeStr(meal.remoteIdMeal),
                area = safeStr(meal.area),
                category = safeStr(meal.category),
                strIngredient1 = safeStr(meal.strIngredient1),
                strIngredient10 = safeStr(meal.strIngredient10),
                strIngredient11 = safeStr(meal.strIngredient11),
                strIngredient12 = safeStr(meal.strIngredient12),
                strIngredient13 = safeStr(meal.strIngredient13),
                strIngredient14 = safeStr(meal.strIngredient14),
                strIngredient15 = safeStr(meal.strIngredient15),
                strIngredient16 = safeStr(meal.strIngredient16),
                strIngredient17 = safeStr(meal.strIngredient17),
                strIngredient18 = safeStr(meal.strIngredient18),
                strIngredient19 = safeStr(meal.strIngredient19),
                strIngredient2 = safeStr(meal.strIngredient2),
                strIngredient20 = safeStr(meal.strIngredient20),
                strIngredient4 = safeStr(meal.strIngredient4),
                strIngredient5 = safeStr(meal.strIngredient5),
                strIngredient6 = safeStr(meal.strIngredient6),
                strIngredient7 = safeStr(meal.strIngredient7),
                strIngredient8 = safeStr(meal.strIngredient8),
                strIngredient9 = safeStr(meal.strIngredient9),
                strInstructions = safeStr(meal.strInstructions),
                name = safeStr(meal.name),
                imageUri = safeStr(meal.imageUri),
                strMeasure1 = safeStr(meal.strMeasure1),
                strMeasure10 = safeStr(meal.strMeasure10),
                strMeasure11 = safeStr(meal.strMeasure11),
                strMeasure12 = safeStr(meal.strMeasure12),
                strMeasure13 = safeStr(meal.strMeasure13),
                strMeasure14 = safeStr(meal.strMeasure14),
                strMeasure15 = safeStr(meal.strMeasure15),
                strMeasure16 = safeStr(meal.strMeasure16),
                strMeasure17 = safeStr(meal.strMeasure17),
                strMeasure18 = safeStr(meal.strMeasure18),
                strMeasure19 = safeStr(meal.strMeasure19),
                strMeasure2 = safeStr(meal.strMeasure2),
                strMeasure20 = safeStr(meal.strMeasure20),
                strMeasure3 = safeStr(meal.strMeasure3),
                strMeasure4 = safeStr(meal.strMeasure4),
                strMeasure5 = safeStr(meal.strMeasure5),
                strMeasure6 = safeStr(meal.strMeasure6),
                strMeasure7 = safeStr(meal.strMeasure7),
                strMeasure8 = safeStr(meal.strMeasure8),
                strMeasure9 = safeStr(meal.strMeasure9),
                strTags = safeStr(meal.tags),
                strYoutube = safeStr(meal.videoLink)
            )

        fun mealDetailsLocalEntityToMeal(meal: MealDetailsLocalEntity): Meal =
            Meal(
                remoteIdMeal = meal.remoteIdMeal,
                name = meal.name,
                imageUri = meal.imageUri,
            )

        fun mealDetailsToPlannedMeal(meal: MealDetails): PlannedMeal =
            PlannedMeal(
                id = meal.id,
                dateToEat = meal.dateToEat,
                mealType = meal.mealType,
                saved = true,

                remoteIdMeal = safeStr(meal.remoteIdMeal),
                area = safeStr(meal.area),
                category = safeStr(meal.category),
                strIngredient1 = safeStr(meal.strIngredient1),
                strIngredient10 = safeStr(meal.strIngredient10),
                strIngredient11 = safeStr(meal.strIngredient11),
                strIngredient12 = safeStr(meal.strIngredient12),
                strIngredient13 = safeStr(meal.strIngredient13),
                strIngredient14 = safeStr(meal.strIngredient14),
                strIngredient15 = safeStr(meal.strIngredient15),
                strIngredient16 = safeStr(meal.strIngredient16),
                strIngredient17 = safeStr(meal.strIngredient17),
                strIngredient18 = safeStr(meal.strIngredient18),
                strIngredient19 = safeStr(meal.strIngredient19),
                strIngredient2 = safeStr(meal.strIngredient2),
                strIngredient20 = safeStr(meal.strIngredient20),
                strIngredient3 = safeStr(meal.strIngredient3),
                strIngredient4 = safeStr(meal.strIngredient4),
                strIngredient5 = safeStr(meal.strIngredient5),
                strIngredient6 = safeStr(meal.strIngredient6),
                strIngredient7 = safeStr(meal.strIngredient7),
                strIngredient8 = safeStr(meal.strIngredient8),
                strIngredient9 = safeStr(meal.strIngredient9),
                strInstructions = safeStr(meal.strInstructions),
                name = safeStr(meal.name),
                imageUri = safeStr(meal.imageUri),
                strMeasure1 = safeStr(meal.strMeasure1),
                strMeasure10 = safeStr(meal.strMeasure10),
                strMeasure11 = safeStr(meal.strMeasure11),
                strMeasure12 = safeStr(meal.strMeasure12),
                strMeasure13 = safeStr(meal.strMeasure13),
                strMeasure14 = safeStr(meal.strMeasure14),
                strMeasure15 = safeStr(meal.strMeasure15),
                strMeasure16 = safeStr(meal.strMeasure16),
                strMeasure17 = safeStr(meal.strMeasure17),
                strMeasure18 = safeStr(meal.strMeasure18),
                strMeasure19 = safeStr(meal.strMeasure19),
                strMeasure2 = safeStr(meal.strMeasure2),
                strMeasure20 = safeStr(meal.strMeasure20),
                strMeasure3 = safeStr(meal.strMeasure3),
                strMeasure4 = safeStr(meal.strMeasure4),
                strMeasure5 = safeStr(meal.strMeasure5),
                strMeasure6 = safeStr(meal.strMeasure6),
                strMeasure7 = safeStr(meal.strMeasure7),
                strMeasure8 = safeStr(meal.strMeasure8),
                strMeasure9 = safeStr(meal.strMeasure9),
                strTags = safeStr(meal.strTags),
                strYoutube = safeStr(meal.strYoutube),

                dateModified = "",
                strCreativeCommonsConfirmed = "",
                strDrinkAlternate = "",
                strImageSource = "",
                strSource = ""
            )

        fun mealEntityToMeal(mealDetailsLocalEntity: MealDetailsLocalEntity): MealRemoteEntity =
            MealRemoteEntity(
                remoteIdMeal = safeStr(mealDetailsLocalEntity.remoteIdMeal),
                name = safeStr(mealDetailsLocalEntity.name),
                imageUri = safeStr(mealDetailsLocalEntity.imageUri)
            )

        fun mealDetailsRemoteToPlanedMeal(meal: MealDetailsRemoteEntity): PlannedMeal =
            PlannedMeal(
                id = meal.id,
                dateToEat = meal.dateToEat,
                mealType = meal.mealType,
                saved = true,

                remoteIdMeal = safeStr(meal.remoteIdMeal),
                area = safeStr(meal.area),
                category = safeStr(meal.category),
                strIngredient1 = safeStr(meal.strIngredient1),
                strIngredient10 = safeStr(meal.strIngredient10),
                strIngredient11 = safeStr(meal.strIngredient11),
                strIngredient12 = safeStr(meal.strIngredient12),
                strIngredient13 = safeStr(meal.strIngredient13),
                strIngredient14 = safeStr(meal.strIngredient14),
                strIngredient15 = safeStr(meal.strIngredient15),
                strIngredient16 = safeStr(meal.strIngredient16),
                strIngredient17 = safeStr(meal.strIngredient17),
                strIngredient18 = safeStr(meal.strIngredient18),
                strIngredient19 = safeStr(meal.strIngredient19),
                strIngredient2 = safeStr(meal.strIngredient2),
                strIngredient20 = safeStr(meal.strIngredient20),
                strIngredient3 = safeStr(meal.strIngredient3),
                strIngredient4 = safeStr(meal.strIngredient4),
                strIngredient5 = safeStr(meal.strIngredient5),
                strIngredient6 = safeStr(meal.strIngredient6),
                strIngredient7 = safeStr(meal.strIngredient7),
                strIngredient8 = safeStr(meal.strIngredient8),
                strIngredient9 = safeStr(meal.strIngredient9),
                strInstructions = safeStr(meal.strInstructions),
                name = safeStr(meal.name),
                imageUri = safeStr(meal.imageUri),
                strMeasure1 = safeStr(meal.strMeasure1),
                strMeasure10 = safeStr(meal.strMeasure10),
                strMeasure11 = safeStr(meal.strMeasure11),
                strMeasure12 = safeStr(meal.strMeasure12),
                strMeasure13 = safeStr(meal.strMeasure13),
                strMeasure14 = safeStr(meal.strMeasure14),
                strMeasure15 = safeStr(meal.strMeasure15),
                strMeasure16 = safeStr(meal.strMeasure16),
                strMeasure17 = safeStr(meal.strMeasure17),
                strMeasure18 = safeStr(meal.strMeasure18),
                strMeasure19 = safeStr(meal.strMeasure19),
                strMeasure2 = safeStr(meal.strMeasure2),
                strMeasure20 = safeStr(meal.strMeasure20),
                strMeasure3 = safeStr(meal.strMeasure3),
                strMeasure4 = safeStr(meal.strMeasure4),
                strMeasure5 = safeStr(meal.strMeasure5),
                strMeasure6 = safeStr(meal.strMeasure6),
                strMeasure7 = safeStr(meal.strMeasure7),
                strMeasure8 = safeStr(meal.strMeasure8),
                strMeasure9 = safeStr(meal.strMeasure9),
                strTags = safeStr(meal.strTags),
                strYoutube = safeStr(meal.strYoutube),

                dateModified = "",
                strCreativeCommonsConfirmed = "",
                strDrinkAlternate = "",
                strImageSource = "",
                strSource = ""
            )

        fun mealEntityToPlanedMeal(mealDetailsLocalEntity: MealDetailsLocalEntity): PlannedMeal =
            PlannedMeal(
                id = mealDetailsLocalEntity.id,
                dateToEat = mealDetailsLocalEntity.dateToEat,
                mealType = mealDetailsLocalEntity.mealType,
                saved = true,

                remoteIdMeal = safeStr(mealDetailsLocalEntity.remoteIdMeal),
                area = safeStr(mealDetailsLocalEntity.area),
                category = safeStr(mealDetailsLocalEntity.category),
                strIngredient1 = safeStr(mealDetailsLocalEntity.strIngredient1),
                strIngredient10 = safeStr(mealDetailsLocalEntity.strIngredient10),
                strIngredient11 = safeStr(mealDetailsLocalEntity.strIngredient11),
                strIngredient12 = safeStr(mealDetailsLocalEntity.strIngredient12),
                strIngredient13 = safeStr(mealDetailsLocalEntity.strIngredient13),
                strIngredient14 = safeStr(mealDetailsLocalEntity.strIngredient14),
                strIngredient15 = safeStr(mealDetailsLocalEntity.strIngredient15),
                strIngredient16 = safeStr(mealDetailsLocalEntity.strIngredient16),
                strIngredient17 = safeStr(mealDetailsLocalEntity.strIngredient17),
                strIngredient18 = safeStr(mealDetailsLocalEntity.strIngredient18),
                strIngredient19 = safeStr(mealDetailsLocalEntity.strIngredient19),
                strIngredient2 = safeStr(mealDetailsLocalEntity.strIngredient2),
                strIngredient20 = safeStr(mealDetailsLocalEntity.strIngredient20),
                strIngredient3 = safeStr(mealDetailsLocalEntity.strIngredient3),
                strIngredient4 = safeStr(mealDetailsLocalEntity.strIngredient4),
                strIngredient5 = safeStr(mealDetailsLocalEntity.strIngredient5),
                strIngredient6 = safeStr(mealDetailsLocalEntity.strIngredient6),
                strIngredient7 = safeStr(mealDetailsLocalEntity.strIngredient7),
                strIngredient8 = safeStr(mealDetailsLocalEntity.strIngredient8),
                strIngredient9 = safeStr(mealDetailsLocalEntity.strIngredient9),
                strInstructions = safeStr(mealDetailsLocalEntity.strInstructions),
                name = safeStr(mealDetailsLocalEntity.name),
                imageUri = safeStr(mealDetailsLocalEntity.imageUri),
                strMeasure1 = safeStr(mealDetailsLocalEntity.strMeasure1),
                strMeasure10 = safeStr(mealDetailsLocalEntity.strMeasure10),
                strMeasure11 = safeStr(mealDetailsLocalEntity.strMeasure11),
                strMeasure12 = safeStr(mealDetailsLocalEntity.strMeasure12),
                strMeasure13 = safeStr(mealDetailsLocalEntity.strMeasure13),
                strMeasure14 = safeStr(mealDetailsLocalEntity.strMeasure14),
                strMeasure15 = safeStr(mealDetailsLocalEntity.strMeasure15),
                strMeasure16 = safeStr(mealDetailsLocalEntity.strMeasure16),
                strMeasure17 = safeStr(mealDetailsLocalEntity.strMeasure17),
                strMeasure18 = safeStr(mealDetailsLocalEntity.strMeasure18),
                strMeasure19 = safeStr(mealDetailsLocalEntity.strMeasure19),
                strMeasure2 = safeStr(mealDetailsLocalEntity.strMeasure2),
                strMeasure20 = safeStr(mealDetailsLocalEntity.strMeasure20),
                strMeasure3 = safeStr(mealDetailsLocalEntity.strMeasure3),
                strMeasure4 = safeStr(mealDetailsLocalEntity.strMeasure4),
                strMeasure5 = safeStr(mealDetailsLocalEntity.strMeasure5),
                strMeasure6 = safeStr(mealDetailsLocalEntity.strMeasure6),
                strMeasure7 = safeStr(mealDetailsLocalEntity.strMeasure7),
                strMeasure8 = safeStr(mealDetailsLocalEntity.strMeasure8),
                strMeasure9 = safeStr(mealDetailsLocalEntity.strMeasure9),
                strTags = safeStr(mealDetailsLocalEntity.tags),
                strYoutube = safeStr(mealDetailsLocalEntity.videoLink),

                dateModified = "",
                strCreativeCommonsConfirmed = "",
                strDrinkAlternate = "",
                strImageSource = "",
                strSource = ""
            )


        fun mealDetailsRemoteEntityToMealDetails(mealDetailsRemoteEntity: MealDetailsRemoteEntity): MealDetails =
            MealDetails(
                remoteIdMeal = safeStr(mealDetailsRemoteEntity.remoteIdMeal),
                area = safeStr(mealDetailsRemoteEntity.area),
                category = safeStr(mealDetailsRemoteEntity.category),
                strIngredient1 = safeStr(mealDetailsRemoteEntity.strIngredient1),
                strIngredient10 = safeStr(mealDetailsRemoteEntity.strIngredient10),
                strIngredient11 = safeStr(mealDetailsRemoteEntity.strIngredient11),
                strIngredient12 = safeStr(mealDetailsRemoteEntity.strIngredient12),
                strIngredient13 = safeStr(mealDetailsRemoteEntity.strIngredient13),
                strIngredient14 = safeStr(mealDetailsRemoteEntity.strIngredient14),
                strIngredient15 = safeStr(mealDetailsRemoteEntity.strIngredient15),
                strIngredient16 = safeStr(mealDetailsRemoteEntity.strIngredient16),
                strIngredient17 = safeStr(mealDetailsRemoteEntity.strIngredient17),
                strIngredient18 = safeStr(mealDetailsRemoteEntity.strIngredient18),
                strIngredient19 = safeStr(mealDetailsRemoteEntity.strIngredient19),
                strIngredient2 = safeStr(mealDetailsRemoteEntity.strIngredient2),
                strIngredient20 = safeStr(mealDetailsRemoteEntity.strIngredient20),
                strIngredient4 = safeStr(mealDetailsRemoteEntity.strIngredient4),
                strIngredient5 = safeStr(mealDetailsRemoteEntity.strIngredient5),
                strIngredient6 = safeStr(mealDetailsRemoteEntity.strIngredient6),
                strIngredient7 = safeStr(mealDetailsRemoteEntity.strIngredient7),
                strIngredient8 = safeStr(mealDetailsRemoteEntity.strIngredient8),
                strIngredient9 = safeStr(mealDetailsRemoteEntity.strIngredient9),
                strInstructions = safeStr(mealDetailsRemoteEntity.strInstructions),
                name = safeStr(mealDetailsRemoteEntity.name),
                imageUri = safeStr(mealDetailsRemoteEntity.imageUri),
                strMeasure1 = safeStr(mealDetailsRemoteEntity.strMeasure1),
                strMeasure10 = safeStr(mealDetailsRemoteEntity.strMeasure10),
                strMeasure11 = safeStr(mealDetailsRemoteEntity.strMeasure11),
                strMeasure12 = safeStr(mealDetailsRemoteEntity.strMeasure12),
                strMeasure13 = safeStr(mealDetailsRemoteEntity.strMeasure13),
                strMeasure14 = safeStr(mealDetailsRemoteEntity.strMeasure14),
                strMeasure15 = safeStr(mealDetailsRemoteEntity.strMeasure15),
                strMeasure16 = safeStr(mealDetailsRemoteEntity.strMeasure16),
                strMeasure17 = safeStr(mealDetailsRemoteEntity.strMeasure17),
                strMeasure18 = safeStr(mealDetailsRemoteEntity.strMeasure18),
                strMeasure19 = safeStr(mealDetailsRemoteEntity.strMeasure19),
                strMeasure2 = safeStr(mealDetailsRemoteEntity.strMeasure2),
                strMeasure20 = safeStr(mealDetailsRemoteEntity.strMeasure20),
                strMeasure3 = safeStr(mealDetailsRemoteEntity.strMeasure3),
                strMeasure4 = safeStr(mealDetailsRemoteEntity.strMeasure4),
                strMeasure5 = safeStr(mealDetailsRemoteEntity.strMeasure5),
                strMeasure6 = safeStr(mealDetailsRemoteEntity.strMeasure6),
                strMeasure7 = safeStr(mealDetailsRemoteEntity.strMeasure7),
                strMeasure8 = safeStr(mealDetailsRemoteEntity.strMeasure8),
                strMeasure9 = safeStr(mealDetailsRemoteEntity.strMeasure9),
                strTags = safeStr(mealDetailsRemoteEntity.strTags),
                strYoutube = safeStr(mealDetailsRemoteEntity.strYoutube)
            )

        fun mealDetailsLocalEntityToMealDetails(mealDetailsLocalEntity: MealDetailsLocalEntity): MealDetails =
            MealDetails(

                id = mealDetailsLocalEntity.id,
                dateToEat = mealDetailsLocalEntity.dateToEat,
                mealType = mealDetailsLocalEntity.mealType,
                saved = true,
                createdAt = mealDetailsLocalEntity.createdAt,

                remoteIdMeal = safeStr(mealDetailsLocalEntity.remoteIdMeal),
                area = safeStr(mealDetailsLocalEntity.area),
                category = safeStr(mealDetailsLocalEntity.category),
                strIngredient1 = safeStr(mealDetailsLocalEntity.strIngredient1),
                strIngredient10 = safeStr(mealDetailsLocalEntity.strIngredient10),
                strIngredient11 = safeStr(mealDetailsLocalEntity.strIngredient11),
                strIngredient12 = safeStr(mealDetailsLocalEntity.strIngredient12),
                strIngredient13 = safeStr(mealDetailsLocalEntity.strIngredient13),
                strIngredient14 = safeStr(mealDetailsLocalEntity.strIngredient14),
                strIngredient15 = safeStr(mealDetailsLocalEntity.strIngredient15),
                strIngredient16 = safeStr(mealDetailsLocalEntity.strIngredient16),
                strIngredient17 = safeStr(mealDetailsLocalEntity.strIngredient17),
                strIngredient18 = safeStr(mealDetailsLocalEntity.strIngredient18),
                strIngredient19 = safeStr(mealDetailsLocalEntity.strIngredient19),
                strIngredient2 = safeStr(mealDetailsLocalEntity.strIngredient2),
                strIngredient20 = safeStr(mealDetailsLocalEntity.strIngredient20),
                strIngredient4 = safeStr(mealDetailsLocalEntity.strIngredient4),
                strIngredient5 = safeStr(mealDetailsLocalEntity.strIngredient5),
                strIngredient6 = safeStr(mealDetailsLocalEntity.strIngredient6),
                strIngredient7 = safeStr(mealDetailsLocalEntity.strIngredient7),
                strIngredient8 = safeStr(mealDetailsLocalEntity.strIngredient8),
                strIngredient9 = safeStr(mealDetailsLocalEntity.strIngredient9),
                strInstructions = safeStr(mealDetailsLocalEntity.strInstructions),
                name = safeStr(mealDetailsLocalEntity.name),
                imageUri = safeStr(mealDetailsLocalEntity.imageUri),
                strMeasure1 = safeStr(mealDetailsLocalEntity.strMeasure1),
                strMeasure10 = safeStr(mealDetailsLocalEntity.strMeasure10),
                strMeasure11 = safeStr(mealDetailsLocalEntity.strMeasure11),
                strMeasure12 = safeStr(mealDetailsLocalEntity.strMeasure12),
                strMeasure13 = safeStr(mealDetailsLocalEntity.strMeasure13),
                strMeasure14 = safeStr(mealDetailsLocalEntity.strMeasure14),
                strMeasure15 = safeStr(mealDetailsLocalEntity.strMeasure15),
                strMeasure16 = safeStr(mealDetailsLocalEntity.strMeasure16),
                strMeasure17 = safeStr(mealDetailsLocalEntity.strMeasure17),
                strMeasure18 = safeStr(mealDetailsLocalEntity.strMeasure18),
                strMeasure19 = safeStr(mealDetailsLocalEntity.strMeasure19),
                strMeasure2 = safeStr(mealDetailsLocalEntity.strMeasure2),
                strMeasure20 = safeStr(mealDetailsLocalEntity.strMeasure20),
                strMeasure3 = safeStr(mealDetailsLocalEntity.strMeasure3),
                strMeasure4 = safeStr(mealDetailsLocalEntity.strMeasure4),
                strMeasure5 = safeStr(mealDetailsLocalEntity.strMeasure5),
                strMeasure6 = safeStr(mealDetailsLocalEntity.strMeasure6),
                strMeasure7 = safeStr(mealDetailsLocalEntity.strMeasure7),
                strMeasure8 = safeStr(mealDetailsLocalEntity.strMeasure8),
                strMeasure9 = safeStr(mealDetailsLocalEntity.strMeasure9),
                strTags = safeStr(mealDetailsLocalEntity.tags),
                strYoutube = safeStr(mealDetailsLocalEntity.videoLink)
            )

        fun mealRemoteEntityToMeal(remoteEntity: MealRemoteEntity): Meal =
            Meal(
                remoteIdMeal = safeStr(remoteEntity.remoteIdMeal),
                name = safeStr(remoteEntity.name),
                imageUri = safeStr(remoteEntity.imageUri),
            )

        fun categoryRemoteEntityToCategory(category: CategoryRemoteEntity): Category =
            Category(
                id = category.id,
                name = category.name,
                des = category.des,
                imageUri = category.imageUri
            )


        private fun safeStr(str: Any?): String = str?.toString() ?: ""
    }



}