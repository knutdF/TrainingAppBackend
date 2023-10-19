package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import redis.clients.jedis.Jedis

class UserRepository(private val redis: Jedis) {

    fun createUser(user: User) {
        // Verwende `HSET` um den Benutzer zu speichern
        redis.hset("user:${user.id}", "id", user.id)
        redis.hset("user:${user.id}", "username", user.username) // Angenommen, User hat ein Attribut `username`
        // Füge weitere Attribute hinzu, falls notwendig
    }

    fun updateUser(user: User) {
        // Überprüfe, ob der Benutzer bereits existiert
        if (redis.exists("user:${user.id}")) {
            // Aktualisiere den Benutzer mit `HSET`
            redis.hset("user:${user.id}", "id", user.id)
            redis.hset("user:${user.id}", "username", user.username)
        } else {
            // Optional: Fehlermeldung, wenn der Benutzer nicht existiert
            println("Benutzer mit ID ${user.id} existiert nicht.")
        }
    }

    fun deleteUser(id: String) {
        // Lösche den Benutzer aus Redis
        redis.del("user:$id")
    }

    fun getUserById(id: String): User? {
        // Verwende `HGET` um den Benutzer abzurufen
        val userId = redis.hget("user:$id", "id")
        val userName = redis.hget("user:$id", "username")
        val userPassword = redis.hget("user:$id", "password")

        if (userId != null && userName != null) {
            return User(id = userId, username = userName, password = userPassword) // Erstelle ein User-Objekt aus den abgerufenen Daten
        }
        return null
    }
}
