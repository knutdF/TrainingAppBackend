package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import io.lettuce.core.api.sync.RedisCommands

class UserRepository(private val redis: RedisCommands<String, String>) {

    fun createUser(user: User) {
        // Verwende `redis` um den Benutzer zu speichern
        redis.set("user:${user.id}", user.toString()) // Beispiel: du müsstest das User-Objekt serialisieren
    }

    fun getUserById(id: String) {
        // Verwende `redis` um den Benutzer abzurufen
        val userData = redis.get("user:$id") // Beispiel: du müsstest das User-Objekt deserialisieren
        // Deine Logik zum Konvertieren der `userData` String-Daten zurück in ein User-Objekt
        // ...
    }

    // Ähnliche Methoden für update und delete
}
