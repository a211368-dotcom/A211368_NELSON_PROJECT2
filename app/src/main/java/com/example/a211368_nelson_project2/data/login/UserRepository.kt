package com.example.a211368_nelson_project2.data.login

import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun registerUser(user: UserEntity) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(username: String, password: String): UserEntity? {
        return userDao.loginUser(username, password)
    }

    suspend fun getUser(username: String): UserEntity? {
        return userDao.getUser(username)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    fun observeUser(username: String): Flow<UserEntity?> {
        return userDao.observeUser(username)
    }
}