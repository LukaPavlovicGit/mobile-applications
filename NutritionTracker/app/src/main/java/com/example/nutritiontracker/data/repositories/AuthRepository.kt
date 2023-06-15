package com.example.nutritiontracker.data.repositories

import com.example.nutritiontracker.dtos.UserDto
import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.dtos.UserRegisterDto
import com.example.nutritiontracker.states.requests.RequestState

interface AuthRepository {
    suspend fun register(userRegisterDto: UserRegisterDto, result: (RequestState<String>) -> Unit)
    suspend fun login(userLoginDto: UserLoginDto, result: (RequestState<UserDto>) -> Unit)
}