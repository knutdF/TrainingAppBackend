package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.repository.UserRepository



class UserService(private val userRepository: UserRepository) {
    fun createUser(user: User): User = userRepository.createUser(user)
    fun getUserById(id: String): User? = userRepository.getUserById(id)
    fun getAllUsers(): List<Map<String, String>> = userRepository.getAllUsers()

    fun updateUser(user: User): User? = userRepository.updateUser(user)
    fun deleteUser(id: String): Unit = userRepository.deleteUser(id)

}
