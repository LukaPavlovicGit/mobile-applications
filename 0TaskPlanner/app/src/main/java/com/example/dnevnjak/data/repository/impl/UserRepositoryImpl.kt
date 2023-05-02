package com.example.dnevnjak.data.repository.impl

import com.example.dnevnjak.data.db.daos.UserDao
import com.example.dnevnjak.data.models.UserEntity
import com.example.dnevnjak.data.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override fun insert(userEntity: UserEntity) = userDao.insert(userEntity)
    override fun update(userEntity: UserEntity) = userDao.update(userEntity)
    override fun getUserByUsernameAndEmail(username: String, email: String) = userDao.getUserByUsernameAndEmail(username, email)
}