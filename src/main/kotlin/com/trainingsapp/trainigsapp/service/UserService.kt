package com.trainingsapp.trainigsapp.service


import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.InMemoryUserRepository

class UserService(private val userRepository: InMemoryUserRepository) {
    suspend fun createUser(user: User): Unit = userRepository.createUser(user)
    suspend fun getUserById(id: String): Unit = userRepository.getUserById(id)
    suspend fun updateUser(user: User): Unit = userRepository.updateUser(user)
    suspend fun deleteUser(id: String): Unit = userRepository.deleteUser(id)
}
