package com.example.dnevnjak.data.repository.impl

import com.example.dnevnjak.data.db.daos.ObligationDao
import com.example.dnevnjak.data.models.ObligationEntity
import com.example.dnevnjak.data.repository.ObligationRepository
import com.example.dnevnjak.utilities.Priority
import kotlinx.coroutines.flow.Flow

class ObligationRepositoryImpl(
    private val obligationDao: ObligationDao
): ObligationRepository {

    override fun insert(obligationEntity: ObligationEntity) = obligationDao.insert(obligationEntity)
    override fun getAll() = obligationDao.getAll()
    override fun getAllByDate(date: Long) = obligationDao.getAllByDate(date)
    override fun getAllByDateAndPriority(dateLong: Long, priority: Priority) = obligationDao.getAllByDateAndPriority(dateLong, priority)
    override fun getAllByDateAndTitle(dateLong: Long, searchQuery: String) = obligationDao.getAllByDateAndTitle(dateLong, searchQuery)
    override fun getAllByDateAndTime(dateLong: Long, time: Long) = obligationDao.getAllByDateAndTime(dateLong, time)
}