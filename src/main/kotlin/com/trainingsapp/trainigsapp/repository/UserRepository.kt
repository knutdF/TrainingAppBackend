package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import redis.clients.jedis.Jedis

class UserRepository(private val redis: Jedis) {

    fun createUser(user: User): User {
        redis.hset("user:${user.id}", mapOf(
            "id" to user.id,
            "username" to user.username,
            "email" to user.email
        ))
        return user
    }

    fun updateUser(user: User): User? {
        if (redis.exists("user:${user.id}")) {
            redis.hset("user:${user.id}", mapOf(
                "id" to user.id,
                "username" to user.username,
                "email" to user.email
            ))
            return user
        } else {
            println("Benutzer mit ID ${user.id} existiert nicht.")
            return null
        }
    }

    fun deleteUser(id: String): Unit {
        if (redis.exists("user:$id")) {
            redis.del("user:$id")
        }
    }


    fun getUserById(id: String): User? {
        val userData = redis.hgetAll("user:$id")

        val userId = userData["id"]
        val userName = userData["username"]
        val userEmail = userData["email"]
        val userPassword = userData["password"]

        if (userId != null && userName != null && userEmail != null) {
            return userPassword?.let { User(id = userId, username = userName, email = userEmail, password = it) }
        }
        return null
    }
}
