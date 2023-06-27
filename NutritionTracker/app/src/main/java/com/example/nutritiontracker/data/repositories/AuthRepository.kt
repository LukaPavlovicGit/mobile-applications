package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.dtos.UserRegisterDto
import com.example.nutritiontracker.states.requests.AuthRequest

interface AuthRepository {
    suspend fun register(userRegisterDto: UserRegisterDto, result: (AuthRequest) -> Unit)
    suspend fun login(userLoginDto: UserLoginDto, result: (AuthRequest) -> Unit)
}