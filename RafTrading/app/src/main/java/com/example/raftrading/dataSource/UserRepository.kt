package com.example.raftrading.dataSource

import com.example.raftrading.dtos.UserDto
import com.example.raftrading.dtos.UserLoginDto
import com.example.raftrading.dtos.UserRegisterDto
import com.example.raftrading.states.RequestState

interface UserRepository {

    suspend fun register(userRegisterDto: UserRegisterDto, result: (RequestState<String>) -> Unit)
    suspend fun login(userLoginDto: UserLoginDto, result: (RequestState<UserDto>) -> Unit)

}