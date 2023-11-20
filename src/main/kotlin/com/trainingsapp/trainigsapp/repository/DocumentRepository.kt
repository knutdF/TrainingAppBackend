package com.trainingsapp.trainigsapp.repository

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
            val documentId = generateUniqueId() // This should generate a Long
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
            return document.copy(documentId = documentId.toString(), createdAt = now, updatedAt = now) // Use the generated Long documentId
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
                val formattedNow = now.format(dateTimeFormatter)

                // Update the document in Redis
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
                        "updatedAt" to formattedNow  // Use formatted string here
                    )
                )

                // Create a new revision
                val revisionNumber = document.documentId?.let { getNextRevisionNumber(it) }
                val newRevision = document.documentId?.let {
                    if (revisionNumber != null) {
                        DocRevision(
                            revisionId = 0,
                            documentId = it,
                            revisionNumber = revisionNumber,
                            revisionDate = now,  // Keep LocalDateTime here
                            changeDescription = changeDescription,
                            responsibleEditor = responsibleEditor
                        )
                    }
                }
                revisionRepository.createRevision(newRevision)

                return document.copy(updatedAt = now)  // Use LocalDateTime object here
            } else {
                logger.warn("Document with ID ${document.documentId} does not exist.")
                return null
            }
        } catch (e: Exception) {
            logger.error("Error updating document", e)
            throw e
        }
    }



    fun deleteDocument(id: String): Boolean {
        return try {
            if (redis.exists("document:$id")) {
                redis.del("document:$id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            logger.error("Error deleting document", e)
            throw e
        }
    }

    fun getAllDocuments(): List<Document> {
        try {
            val documentKeys = redis.keys("document:*")
            return documentKeys.mapNotNull { key ->
                val documentData = redis.hgetAll(key)
                if (documentData.isNotEmpty()) {
                    Document(
                        documentId = (documentData["documentId"]?.toLong() ?: 0L).toString(),
                        title = documentData["title"] ?: "",
                        type = documentData["type"],
                        creationDate = LocalDateTime.parse(documentData["creationDate"] ?: LocalDateTime.now().toString()),
                        lastReviewDate = LocalDateTime.parse(documentData["lastReviewDate"] ?: LocalDateTime.now().toString()),
                        responsibleAuthor = documentData["responsibleAuthor"] ?: "",
                        status = documentData["status"] ?: "",
                        createdAt = LocalDateTime.parse(documentData["createdAt"] ?: LocalDateTime.now().toString()),
                        updatedAt = LocalDateTime.parse(documentData["updatedAt"] ?: LocalDateTime.now().toString())
                    )
                } else {
                    logger.error("Document data for key $key is empty or missing.")
                    null
                }
            }
        } catch (e: Exception) {
            logger.error("Error fetching all documents", e)
            throw e
        }
    }

    fun getDocumentById(id: String): Document? {
        try {
            val documentData = redis.hgetAll("document:$id")
            if (documentData.isNotEmpty()) {
                return Document(
                    documentId = (documentData["documentId"]?.toLong() ?: 0L).toString(),
                    title = documentData["title"] ?: "",
                    type = documentData["type"],
                    creationDate = LocalDateTime.parse(documentData["creationDate"] ?: LocalDateTime.now().toString()),
                    lastReviewDate = LocalDateTime.parse(documentData["lastReviewDate"] ?: LocalDateTime.now().toString()),
                    responsibleAuthor = documentData["responsibleAuthor"] ?: "",
                    status = documentData["status"] ?: "",
                    createdAt = LocalDateTime.parse(documentData["createdAt"] ?: LocalDateTime.now().toString()),
                    updatedAt = LocalDateTime.parse(documentData["updatedAt"] ?: LocalDateTime.now().toString())
                )
            } else {
                return null
            }
        } catch (e: Exception) {
            logger.error("Error fetching document by ID", e)
            throw e
        }
    }

    private fun generateUniqueId() {
        // Implement logic to generate a unique ID for each document
    }

    private fun getNextRevisionNumber(documentId: String) {
        // Implement logic to get the next revision number for the given document
    }


}


