package com.example.nutritiontracker.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nutritiontracker.data.datasource.local.entities.MealEntity
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert
    fun insert(meal: MealEntity): Long

    @Query("SELECT * FROM meals ORDER BY strArea ASC")
    fun getAll(): List<MealEntity>

    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Long): Int

    @Update
    fun update(meal: MealEntity): Int

    @Query("SELECT * FROM meals WHERE idMeal = :idMeal")
    fun findByIdMeal(idMeal: String): MealEntity?
}