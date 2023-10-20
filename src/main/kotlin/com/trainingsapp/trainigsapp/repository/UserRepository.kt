package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import redis.clients.jedis.Jedis

class UserRepository(private val redis: Jedis) {

    fun createUser(user: User) {
        // Verwende `HSET` um den Benutzer zu speichern
        redis.hset("user:${user.id}", mapOf(
            "id" to user.id,
            "username" to user.username,
            "email" to user.email // Füge das email-Attribut hinzu
        ))
    }

    fun updateUser(user: User) {
        // Überprüfe, ob der Benutzer bereits existiert
        if (redis.exists("user:${user.id}")) {
            // Aktualisiere den Benutzer mit `HSET`
            redis.hset("user:${user.id}", mapOf(
                "id" to user.id,
                "username" to user.username,
                "email" to user.email // Füge das email-Attribut hinzu
            ))
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
        // Verwende `HGETALL` um alle Attribute des Benutzers abzurufen
        val userData = redis.hgetAll("user:$id")

        val userId = userData["id"]
        val userName = userData["username"]
        val userEmail = userData["email"] // Füge das email-Attribut hinzu
        val userPassword = userData["password"]

        if (userId != null && userName != null && userEmail != null) {
            return userPassword?.let { User(id = userId, username = userName, email = userEmail, password = it) } // Erstelle ein User-Objekt aus den abgerufenen Daten
        }
        return null
    }
}
