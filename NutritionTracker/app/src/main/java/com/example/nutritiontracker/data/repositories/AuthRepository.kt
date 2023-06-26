package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.dtos.UserRegisterDto
import com.example.nutritiontracker.states.requests.AuthRequestState

interface AuthRepository {
    suspend fun register(userRegisterDto: UserRegisterDto, result: (AuthRequestState) -> Unit)
    suspend fun login(userLoginDto: UserLoginDto, result: (AuthRequestState) -> Unit)
}