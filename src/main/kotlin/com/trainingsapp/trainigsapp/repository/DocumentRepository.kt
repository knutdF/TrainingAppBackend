package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.Document
import io.lettuce.core.api.sync.RedisCommands

class DocumentRepository(private val redis: RedisCommands<String, String>) {

    fun createDocument(document: Document) {
        // Beispiel: Serialisieren Sie das Document-Objekt und speichern Sie es in Redis
        redis.set("document:${document.id}", document.toString())
    }

    fun getDocumentById(id: String) {
        // Beispiel: Holen Sie die Daten von Redis und deserialisieren Sie sie zu einem Document-Objekt
        val documentData = redis.get("document:$id")
        // Konvertieren Sie `documentData` zurück in ein Document-Objekt
        // ...
    }

    // ... Weitere CRUD-Operationen (Update, Delete)
}
