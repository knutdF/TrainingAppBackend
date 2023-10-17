package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import redis.clients.jedis.Jedis

class UserRepository(private val redis: Jedis) {

    fun createUser(user: User) {
        // Verwende `HSET` um den Benutzer zu speichern
        redis.hset("user:${user.id}", "id", user.id)
        redis.hset("user:${user.id}", "name", user.username) // Angenommen, User hat ein Attribut `name`
        // Füge weitere Attribute hinzu, falls notwendig
    }

    fun getUserById(id: String): User? {
        // Verwende `HGET` um den Benutzer abzurufen
        val userId = redis.hget("user:$id", "id")
        val userName = redis.hget("user:$id", "name")

        if (userId != null && userName != null) {
            return User(id = userId, name = userName) // Erstelle ein User-Objekt aus den abgerufenen Daten
        }
        return null
    }

}
