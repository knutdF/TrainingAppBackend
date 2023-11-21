package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.DocRevision
import com.trainingsapp.trainigsapp.model.Document
import com.trainingsapp.trainigsapp.repository.DocumentRepository
import com.trainingsapp.trainigsapp.repository.RevisionRepository
import java.time.LocalDateTime

class DocumentService(
    private val documentRepository: DocumentRepository,
    private val revisionRepository: RevisionRepository
) {

    fun createDocument(document: Document): Document {
        return documentRepository.createDocument(document)
    }


    fun getDocumentById(id: String): Document? {
        return documentRepository.getDocumentById(id)
    }

    fun getAllDocuments(): List<Document> {
        return documentRepository.getAllDocuments()
    }

    fun updateDocument(document: Document, responsibleAuthor: String): Document? {
        val updatedDocument = documentRepository.updateDocument(document)
        updatedDocument?.let {
            val revisionNumber = getNextRevisionNumber(document.documentId)
            val newRevision = DocRevision(
                revisionId = generateRevisionId(), // Angenommen, Sie haben eine Methode, um eine eindeutige RevisionId zu generieren
                documentId = document.documentId,
                revisionNumber = revisionNumber,
                revisionDate = LocalDateTime.now(),
                responsibleAuthor = responsibleAuthor
            )
            revisionRepository.createRevision(newRevision)
        }
        return updatedDocument
    }


    private fun getNextRevisionNumber(documentId: String): Int {
        val latestRevision = revisionRepository.getLatestRevision(documentId)
        return if (latestRevision != null) {
            latestRevision.revisionNumber + 1
        } else {
            1 // Starten Sie mit 1, wenn noch keine Revisionen existieren
        }
    }

    private fun generateRevisionId(): Long {
        // Implementieren Sie hier die Logik zur Generierung einer eindeutigen RevisionId
    }

    fun deleteDocument(id: String): Boolean {
        return documentRepository.deleteDocument(id)
    }

}
