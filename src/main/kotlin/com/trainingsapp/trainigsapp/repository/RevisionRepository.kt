package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.DocRevision
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.slf4j.LoggerFactory

class RevisionRepository(private val redis: Jedis) {
    private val logger = LoggerFactory.getLogger(RevisionRepository::class.java)
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun createRevision(revision: DocRevision): DocRevision {
        try {
            val revisionId = generateUniqueId()
            val now = LocalDateTime.now()

            redis.hset(
                "revision:$revisionId", mapOf(
                    "revisionId" to revisionId.toString(),
                    "documentId" to revision.documentId.toString(),
                    "revisionNumber" to revision.revisionNumber.toString(),
                    "revisionDate" to now.format(dateTimeFormatter),
                    "changeDescription" to (revision.changeDescription ?: ""),
                    "responsibleEditor" to revision.responsibleEditor
                )
            )
            return revision.copy(revisionId = revisionId, revisionDate = now)
        } catch (e: Exception) {
            logger.error("Error creating revision", e)
            throw e
        }
    }

    private fun generateUniqueId(): Long {
        // Implementieren Sie hier die Logik zur Generierung einer eindeutigen ID
    }
}
