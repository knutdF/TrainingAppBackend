package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.DocRevision
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.slf4j.LoggerFactory

class RevisionRepository(private val redis: Jedis) {
    private val logger = LoggerFactory.getLogger(RevisionRepository::class.java)
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val revisionCounterKey = "revision:counter"

    fun createRevision(revision: DocRevision): DocRevision {
        try {
            val revisionId = generateUniqueId()
            val now = LocalDateTime.now()

            redis.hset(
                "revision:$revisionId", mapOf(
                    "revisionId" to revisionId.toString(),
                    "documentId" to revision.documentId,
                    "revisionNumber" to revision.revisionNumber.toString(), // Konvertieren Sie revisionNumber in einen String
                    "revisionDate" to now.format(dateTimeFormatter) // Stellen Sie sicher, dass auch dies ein String ist
                    // "changeDescription" und "responsibleEditor" hinzufügen, falls benötigt
                )
            )
            return revision.copy(revisionId = revisionId, revisionDate = now)
        } catch (e: Exception) {
            logger.error("Error creating revision", e)
            throw e
        }
    }

    private fun generateUniqueId(): Int {
        return redis.incr(revisionCounterKey).toInt()
    }
}
