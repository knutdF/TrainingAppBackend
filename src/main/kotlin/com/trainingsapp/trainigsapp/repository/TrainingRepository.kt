package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.TrainingSession
import io.lettuce.core.api.sync.RedisCommands

class TrainingSessionRepository(private val redis: RedisCommands<String, String>) {

    fun createSession(session: TrainingSession) {
        // Beispiel: Serialisieren und speichern Sie das TrainingSession-Objekt in Redis
        redis.set("session:${session.id}", session.toString())
    }

    fun getSessionById(id: String) {
        // Beispiel: Holen und deserialisieren Sie das TrainingSession-Objekt aus Redis
        val sessionData = redis.get("session:$id")
        // Konvertieren Sie `sessionData` zurück in ein TrainingSession-Objekt
        // ...
    }

    // ... Weitere CRUD-Operationen (Update, Delete)
}
