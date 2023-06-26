package com.example.nutritiontracker.data.repositories.impl

import com.example.nutritiontracker.data.repositories.AuthRepository
import com.example.nutritiontracker.dtos.UserDto
import com.example.nutritiontracker.dtos.UserLoginDto
import com.example.nutritiontracker.dtos.UserRegisterDto
import com.example.nutritiontracker.states.requests.AuthRequestState
import com.example.nutritiontracker.states.requests.RetrofitRequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthRepositoryImpl (
    private val auth: FirebaseAuth
): AuthRepository {
    override suspend fun register(userRegisterDto: UserRegisterDto, result: (AuthRequestState) -> Unit) {
        auth.createUserWithEmailAndPassword(userRegisterDto.email, userRegisterDto.password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    result.invoke(AuthRequestState.Success)
                }
                else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(AuthRequestState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(AuthRequestState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(AuthRequestState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(AuthRequestState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {  result.invoke(AuthRequestState.Failure()) }
    }

    override suspend fun login(userLoginDto: UserLoginDto, result: (AuthRequestState) -> Unit) {
        auth.signInWithEmailAndPassword(userLoginDto.email, userLoginDto.password)
            .addOnCompleteListener{
                if(it.isSuccessful) result.invoke(AuthRequestState.Success)
                else result.invoke(AuthRequestState.Failure())
            }
            .addOnFailureListener {
                result.invoke(AuthRequestState.Failure())
            }
    }
}