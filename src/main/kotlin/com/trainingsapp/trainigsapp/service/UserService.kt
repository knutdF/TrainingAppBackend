package com.trainingsapp.trainigsapp.service


import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainingsapp.InMemoryUserRepository

class UserService(private val userRepository: InMemoryUserRepository) {
    suspend fun createUser(user: User): User = userRepository.createUser(user)
    suspend fun getUserById(id: String): User? = userRepository.getUserById(id)
    suspend fun updateUser(user: User): User = userRepository.updateUser(user)
    suspend fun deleteUser(id: String): Boolean = userRepository.deleteUser(id)
}
