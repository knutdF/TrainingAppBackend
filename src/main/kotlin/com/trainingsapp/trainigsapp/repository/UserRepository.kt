package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User

interface UserRepository {
    suspend fun createUser(user: User): User
    suspend fun getUserById(id: String): User?
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: String): Boolean
}
