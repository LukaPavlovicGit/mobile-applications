package com.example.dnevnjak.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dnevnjak.data.models.ObligationEntity
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface ObligationDao {

    @Insert
    fun insert(obligation: ObligationEntity)

    @Delete
    fun delete(obligation: ObligationEntity)

    @Update
    fun update(obligationEntity: ObligationEntity)

    @Query("SELECT * FROM obligations")
    fun getAll(): Flow<List<ObligationEntity>>

    @Query("SELECT * FROM obligations WHERE date = :dateLong")
    fun getAllByDate(dateLong: Long): Flow<List<ObligationEntity>>

}