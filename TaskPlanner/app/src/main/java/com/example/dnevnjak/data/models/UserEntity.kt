package com.example.dnevnjak.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["username", "email"], unique = true)])
data class UserEntity(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)