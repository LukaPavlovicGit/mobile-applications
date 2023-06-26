package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.dtos.UserDto
import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.dtos.UserRegisterDto
import com.example.nutritiontracker.states.requests.RetrofitRequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthRepositoryImpl (
    private val auth: FirebaseAuth
): AuthRepository {
    override suspend fun register(userRegisterDto: UserRegisterDto, result: (RetrofitRequestState<String>) -> Unit) {
        auth.createUserWithEmailAndPassword(userRegisterDto.email, userRegisterDto.password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    result.invoke(RetrofitRequestState.Success(null,"User registered successfully"))
//                    val userDto = UserDto(it.result.user?.uid!!, userRegisterDto.username, userRegisterDto.email, userRegisterDto.password)
//                    saveUser(userDto){ state ->
//                        when(state){
//                            is RequestState.Success -> result.invoke(RequestState.Success(null,"User registered successfully"))
//                            is RequestState.Failure -> result.invoke(RequestState.Failure("User registered successfully but session failed to store"))
//                            RequestState.Processing -> TODO()
//                        }
//                    }
                }
                else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(RetrofitRequestState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(RetrofitRequestState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(RetrofitRequestState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(RetrofitRequestState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener { result.invoke(RetrofitRequestState.Failure(it.localizedMessage)) }
    }

    override suspend fun login(userLoginDto: UserLoginDto, result: (RetrofitRequestState<UserDto>) -> Unit) {
        auth.signInWithEmailAndPassword(userLoginDto.email, userLoginDto.password)
            .addOnCompleteListener{
                if(it.isSuccessful) result.invoke(RetrofitRequestState.Success(UserDto(id = it.result.user?.uid!!, email = userLoginDto.email),"Successful"))
                else result.invoke(RetrofitRequestState.Failure("Invalid credentials..."))
            }
            .addOnFailureListener {
                result.invoke(RetrofitRequestState.Failure(it.localizedMessage))
            }
    }
}