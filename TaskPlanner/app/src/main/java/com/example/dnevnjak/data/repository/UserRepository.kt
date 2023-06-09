package com.example.dnevnjak.data.repository

import com.example.dnevnjak.data.models.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun insert(userEntity: UserEntity)
    fun update(userEntity: UserEntity)
    fun getUserByUsernameAndEmail(username: String, email: String) : Flow<UserEntity>
}