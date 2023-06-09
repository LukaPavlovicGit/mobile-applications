package com.example.dnevnjak.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.data.models.priorityEnum.Priority
import kotlinx.coroutines.flow.Flow

@Dao
interface ObligationDao {

    @Insert
    fun insert(obligation: ObligationEntity)

    @Query("DELETE FROM obligations WHERE id = :id ")
    fun delete(id: Int)

    @Update
    fun update(obligationEntity: ObligationEntity)

    @Query("SELECT * FROM obligations ORDER BY start ASC")
    fun getAll(): Flow<List<ObligationEntity>>

    @Query("SELECT * FROM obligations WHERE date = :dateLong ORDER BY start ASC")
    fun getAllByDate(dateLong: Long): Flow<List<ObligationEntity>>

    @Query("SELECT * FROM obligations WHERE date = :dateLong AND start > :time ORDER BY start ASC")
    fun getAllByDateAndTime(dateLong: Long, time: Long): Flow<List<ObligationEntity>>

    @Query("SELECT * FROM obligations WHERE date =:dateLong AND priority =:priority ORDER BY start ASC")
    fun getAllByDateAndPriority(dateLong: Long, priority: Priority): Flow<List<ObligationEntity>>

    @Query("SELECT * FROM obligations WHERE date =:dateLong AND title LIKE :searchQuery||'%' ORDER BY start ASC")
    fun getAllByDateAndTitle(dateLong: Long, searchQuery: String): Flow<List<ObligationEntity>>

}