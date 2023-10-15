package com.trainingsapp.trainigsapp.service


import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    suspend fun createUser(user: User): User = userRepository.createUser(user)
    suspend fun getUserById(id: String): User? = userRepository.getUserById(id)
    suspend fun updateUser(user: User): User = userRepository.updateUser(user)
    suspend fun deleteUser(id: String): Boolean = userRepository.deleteUser(id)
}
