package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.Department
import com.trainingsapp.trainigsapp.model.Document
import com.trainingsapp.trainigsapp.model.DocRevision
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DocumentRepository(private val redis: Jedis, private val revisionRepository: RevisionRepository) {
    private val logger = LoggerFactory.getLogger(DocumentRepository::class.java)
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun createDocument(document: Document): Document {
        try {
            val documentId = generateUniqueId()
            val now = LocalDateTime.now()

            redis.hset(
                "document:$documentId", mapOf(
                    "documentId" to documentId.toString(),
                    "title" to document.title,
                    "type" to (document.type ?: ""),
                    "departmentid" to (document.departmentid ?: ""),
                    "creationDate" to document.creationDate.format(dateTimeFormatter),
                    "lastReviewDate" to document.lastReviewDate.format(dateTimeFormatter),
                    "responsibleAuthor" to document.responsibleAuthor,
                    "status" to document.status,
                    "createdAt" to document.createdAt.format(dateTimeFormatter),
                    "updatedAt" to now.format(dateTimeFormatter)
                )
            )
            return document.copy(documentId = documentId.toString(), createdAt = now, updatedAt = now)
        } catch (e: Exception) {
            logger.error("Error creating document", e)
            throw e
        }
    }

    fun updateDocument(document: Document, changeDescription: String, responsibleEditor: String): Document? {
        try {
            val documentKey = "document:${document.documentId}"
            if (redis.exists(documentKey)) {
                val now = LocalDateTime.now()

                redis.hset(
                    documentKey, mapOf(
                        "title" to document.title,
                        "type" to (document.type ?: ""),
                        "departmentid" to (document.departmentid ?: ""),
                        "creationDate" to document.creationDate.format(dateTimeFormatter),
                        "lastReviewDate" to document.lastReviewDate.format(dateTimeFormatter),
                        "responsibleAuthor" to document.responsibleAuthor,
                        "status" to document.status,
                        "createdAt" to document.createdAt.format(dateTimeFormatter),
                        "updatedAt" to now.format(dateTimeFormatter)
                    )
                )

                val revisionNumber = document.documentId?.let { getNextRevisionNumber(it) }
                val newRevision = document.documentId?.let {
                    DocRevision(
                        revisionId = 0, // Wird im Repository generiert
                        documentId = it,
                        revisionNumber = revisionNumber,
                        revisionDate = now,
                        responsibleAuthor = responsibleEditor
                    )
                }
                if (newRevision != null) {
                    revisionRepository.createRevision(newRevision)
                }

                return document.copy(updatedAt = now)
            } else {
                logger.warn("Document with ID ${document.documentId} does not exist.")
                return null
            }
        } catch (e: Exception) {
            logger.error("Error updating document", e)
            throw e
        }
    }

    private fun generateUniqueId(): Long {
        // Logik zur Generierung einer einzigartigen ID für jedes Dokument
        // Zum Beispiel könnte dies eine Zufallszahl oder eine Sequenznummer sein
        // Beispiel:
        return redis.incr("documentId:counter")
    }

    private fun getNextRevisionNumber(documentId: String): Int {
        // Logik zur Bestimmung der nächsten Revisionsnummer für das gegebene Dokument
        // Beispiel:
        val revisionCounterKey = "revision:counter:$documentId"
        return redis.incr(revisionCounterKey).toInt()
    }

    fun getDocumentById(documentId: String): Document? {
        try {
            val documentData = redis.hgetAll("document:$documentId")
            if (documentData.isNotEmpty()) {
                return Document(
                    documentId = documentData["documentId"] ?: "",
                    title = documentData["title"] ?: "",
                    type = documentData["type"],
                    departmentid = documentData["departmentid"],
                    creationDate = LocalDateTime.parse(documentData["creationDate"], dateTimeFormatter),
                    lastReviewDate = LocalDateTime.parse(documentData["lastReviewDate"], dateTimeFormatter),
                    responsibleAuthor = documentData["responsibleAuthor"] ?: "",
                    status = documentData["status"] ?: "",
                    createdAt = LocalDateTime.parse(documentData["createdAt"], dateTimeFormatter),
                    updatedAt = LocalDateTime.parse(documentData["updatedAt"], dateTimeFormatter)
                )
            } else {
                return null
            }
        } catch (e: Exception) {
            logger.error("Error fetching document by ID $documentId", e)
            throw e
        }
    }


    private fun deleteDocument(documentId: String) {
        // Logik zur Löschung eines Dokuments anhand seiner ID
        // Beispiel:
        val documentKey = "document:$documentId"
        redis.del(documentKey)
    }



    // ... Weitere Methoden für deleteDocument, getAllDocuments, getDocumentById ...
}
