package com.example.nutritiontracker.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nutritiontracker.data.datasource.local.entities.MealDetailsLocalEntity

@Dao
interface MealDao {

    @Insert
    fun insert(meal: MealDetailsLocalEntity): Long

    @Query("SELECT * FROM meals ORDER BY area ASC")
    fun getAll(): List<MealDetailsLocalEntity>

    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Long): Int

    @Update
    fun update(meal: MealDetailsLocalEntity): Int

    @Query("SELECT * FROM meals WHERE remoteIdMeal = :remoteIdMeal")
    fun findByIdMeal(remoteIdMeal: String): MealDetailsLocalEntity?

    @Query("SELECT * FROM meals WHERE id = :id")
    fun findById(id: Long): MealDetailsLocalEntity?
}