package com.trainingsapp.trainigsapp.repository


import com.trainingsapp.trainigsapp.model.DocRevision
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.slf4j.LoggerFactory

class RevisionRepository(private val redis: Jedis) {
    private val logger = LoggerFactory.getLogger(RevisionRepository::class.java)

    fun createRevision(revision: Unit?): DocRevision {
        try {
            val revisionId = generateUniqueId()
            val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            redis.hset(
                "revision:$revisionId", mapOf(
                    "revisionId" to revisionId.toString(),
                    "documentId" to revision.documentId.toString(),
                    "revisionNumber" to revision.revisionNumber.toString(),
                    "revisionDate" to now,
                    "changeDescription" to (revision.changeDescription ?: ""),
                    "responsibleEditor" to revision.responsibleEditor
                )
            )
            return revision.copy(revisionId = revisionId.toString(), revisionDate = now)
        } catch (e: Exception) {
            logger.error("Error creating revision", e)
            throw e
        }
    }

    // Weitere Methoden für das Abrufen und Aktualisieren von Revisionen

    private fun generateUniqueId() {
        // Implementieren Sie die Logik zur Generierung einer eindeutigen ID für jede Revision
    }
}
