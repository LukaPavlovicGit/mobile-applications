package com.example.dnevnjak.data.repository

import com.example.dnevnjak.data.models.ObligationEntity
import kotlinx.coroutines.flow.Flow

interface ObligationRepository {
    fun insert(obligationEntity: ObligationEntity)
    fun getAll(): Flow<List<ObligationEntity>>
}