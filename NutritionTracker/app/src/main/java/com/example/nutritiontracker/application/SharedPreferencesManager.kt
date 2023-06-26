package com.example.nutritiontracker.application

import android.content.SharedPreferences
import com.example.nutritiontracker.dtos.UserDto
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPref: SharedPreferences
) {

    fun isAuthenticated(): Boolean =
        sharedPref.getString("email", "")!!.isNotEmpty()

    fun saveUser(email: String) =
        sharedPref
            .edit()
            .putString("email", email)
            .apply()

    fun clearRegistration() =
        sharedPref
            .edit()
            .remove("email")
            .apply()

    fun getAuthenticatedUser(): UserDto {
        val id = sharedPref.getString("id", "")
        val username = sharedPref.getString("username", "")
        val email = sharedPref.getString("email", "")

        return UserDto(id = id!!, username = username!!, email = email!!)
    }

}