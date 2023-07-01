package com.example.nutritiontracker.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nutritiontracker.data.converters.LocalDateConverter
import com.example.nutritiontracker.data.datasource.local.dao.MealDao
import com.example.nutritiontracker.data.datasource.local.entities.MealDetailsLocalEntity

@Database(
    entities = [MealDetailsLocalEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun getMealDao(): MealDao
}