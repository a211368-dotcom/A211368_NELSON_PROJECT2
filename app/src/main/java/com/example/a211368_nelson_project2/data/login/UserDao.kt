package com.example.a211368_nelson_project2.data.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM user_profiles WHERE username = :username AND password = :password LIMIT 1")
    suspend fun loginUser(username: String, password: String): UserEntity?

    @Query("SELECT * FROM user_profiles WHERE username = :username LIMIT 1")
    suspend fun getUser(username: String): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user_profiles WHERE username = :username LIMIT 1")
    fun observeUser(username: String): Flow<UserEntity?>
}