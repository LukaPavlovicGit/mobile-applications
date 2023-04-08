package com.example.dnevnjak.data.repository.impl

import com.example.dnevnjak.data.db.daos.ObligationDao
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.data.repository.ObligationRepository
import kotlinx.coroutines.flow.Flow

class ObligationRepositoryImpl(
    private val obligationDao: ObligationDao
): ObligationRepository {

    override fun insert(obligationEntity: ObligationEntity){
        obligationDao.insert(obligationEntity)
    }

    override fun getAll() = obligationDao.getAll()

}