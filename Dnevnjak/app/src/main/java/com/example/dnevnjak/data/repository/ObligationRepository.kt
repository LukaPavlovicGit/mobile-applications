package com.example.dnevnjak.data.repository

import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.utilities.Priority
import kotlinx.coroutines.flow.Flow

interface ObligationRepository {
    fun insert(obligationEntity: ObligationEntity)
    fun update(obligationEntity: ObligationEntity)
    fun getAll(): Flow<List<ObligationEntity>>
    fun getAllByDate(date: Long): Flow<List<ObligationEntity>>
    fun getAllByDateAndPriority(dateLong: Long, priority: Priority): Flow<List<ObligationEntity>>
    fun getAllByDateAndTitle(dateLong: Long, searchQuery: String): Flow<List<ObligationEntity>>
    fun getAllByDateAndTime(dateLong: Long, time: Long): Flow<List<ObligationEntity>>
}