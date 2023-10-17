package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import redis.clients.jedis.Jedis

class UserRepository(private val redis: Jedis) {

    fun createUser(user: User) {
        // Verwende `hmset` um alle Benutzerattribute in einem Aufruf zu setzen
        val userMap = mapOf(
            "id" to user.id,
            "username" to user.username
        )
        redis.hmset("user:${user.id}", userMap)
    }

    fun getUserById(id: String): User? {
        // Verwende `hmget` um alle Benutzerattribute in einem Aufruf abzurufen
        val attributes = listOf("id", "username")
        val values = redis.hmget("user:$id", attributes)

        val userId = values[0]
        val userName = values[1]

        if (userId != null && userName != null) {
            return User(id = userId, username = userName) // Erstelle ein User-Objekt aus den abgerufenen Daten
        }
        return null
    }
}
