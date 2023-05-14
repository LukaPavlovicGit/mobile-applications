package com.example.raftrading.application

import android.content.SharedPreferences
import com.example.raftrading.dtos.UserDto
import javax.inject.Inject
import javax.inject.Singleton

class SharedPreferencesManager @Inject constructor(
    private val sharedPref: SharedPreferences
) {

    fun isAuthenticated(): Boolean =
        sharedPref.getString("username", "")!!.isNotEmpty()

    fun saveUser(userDto: UserDto) =
        sharedPref
            .edit()
            .putString("id", userDto.id)
            .putString("username", userDto.username)
            .putString("email", userDto.email)
            .apply()

    fun clearRegistration() =
        sharedPref
            .edit()
            .remove("id")
            .remove("username")
            .remove("email")
            .apply()

    fun getAuthenticatedUser(): UserDto {
        val id = sharedPref.getString("id", "")
        val username = sharedPref.getString("username", "")
        val email = sharedPref.getString("email", "")

        return UserDto(id = id!!, username = username!!, email = email!!)
    }

}