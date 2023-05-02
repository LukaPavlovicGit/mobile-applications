package com.example.dnevnjak.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dnevnjak.data.models.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun insert(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username AND email = :email")
    fun getUserByUsernameAndEmail(username: String, email: String ) : Flow<UserEntity>
}