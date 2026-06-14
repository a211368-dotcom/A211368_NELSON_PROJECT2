package com.example.a211368_nelson_project2.data.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserEntity(
    @PrimaryKey
    val username: String,
    val password: String,
    val email: String = "",
    val age: String = "",
    val className: String = "",
    val profileImage: String = ""
)