package com.example.raftrading.dataSource.firebaseDB

import com.example.raftrading.dataSource.UserRepository
import com.example.raftrading.dtos.UserDto
import com.example.raftrading.dtos.UserLoginDto
import com.example.raftrading.dtos.UserRegisterDto
import com.example.raftrading.states.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference

class UserRepositoryFirebaseImpl (
    private val auth: FirebaseAuth,
    private val usersRoot: DatabaseReference
) : UserRepository {

    override suspend fun register(userRegisterDto: UserRegisterDto, result: (RequestState<String>) -> Unit) {
        auth.createUserWithEmailAndPassword(userRegisterDto.email, userRegisterDto.password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val userDto = UserDto(it.result.user?.uid!!, userRegisterDto.username, userRegisterDto.email, userRegisterDto.password)
                    saveUser(userDto){ state ->
                        when(state){
                            is RequestState.Success -> result.invoke(RequestState.Success(null,"User registered successfully"))
                            is RequestState.Failure -> result.invoke(RequestState.Failure("User registered successfully but session failed to store"))
                            RequestState.Processing -> TODO()
                        }
                    }
                }
                else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(RequestState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(RequestState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(RequestState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(RequestState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener { result.invoke(RequestState.Failure(it.localizedMessage)) }
    }

    override suspend fun login(userLoginDto: UserLoginDto, result: (RequestState<UserDto>) -> Unit) {
        auth.signInWithEmailAndPassword(userLoginDto.email, userLoginDto.password)
            .addOnCompleteListener{
                if(it.isSuccessful) result.invoke(RequestState.Success(UserDto(id = it.result.user?.uid!!, email = userLoginDto.email),"Successful"))
                else result.invoke(RequestState.Failure("Invalid credentials..."))
            }
            .addOnFailureListener {
                result.invoke(RequestState.Failure(it.localizedMessage))
            }
    }

    private fun saveUser(userDto: UserDto, result: (RequestState<String>) -> Unit){
        val map = hashMapOf<String, Any>()
        map["username"] = userDto.username
        map["email"] = userDto.email

        val userRef =  usersRoot.child(userDto.id)
        userRef.setValue(map)
            .addOnCompleteListener {
                if(it.isSuccessful) result.invoke(RequestState.Success(null,"User has been saved successfully"))
                else result.invoke(RequestState.Failure(it.exception?.localizedMessage))
            }
            .addOnFailureListener {
                result.invoke(RequestState.Failure(it.localizedMessage))
            }

    }
}