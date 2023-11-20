package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.DocRevision
import com.trainingsapp.trainigsapp.model.Document
import com.trainingsapp.trainigsapp.repository.DocumentRepository
import kotlinx.datetime.LocalDateTime

class DocumentService(private val documentRepository: DocumentRepository) {

    fun createDocument(document: Document): Document {
        return documentRepository.createDocument(document)
    }

    fun getDocumentById(id: String): Document? {
        return documentRepository.getDocumentById(id)
    }

    fun getAllDocuments(): List<Document> {
        return documentRepository.getAllDocuments()
    }

    fun updateDocument(document: Document, changeDescription: String, responsibleEditor: String): Document? {
        val updatedDocument = documentRepository.updateDocument(document)
        updatedDocument?.let {
            val revisionNumber = getNextRevisionNumber(document.documentId)
            val newRevision = DocRevision(
                revisionId = 0, // Wird im Repository generiert
                documentId = document.documentId,
                revisionNumber = revisionNumber,
                revisionDate = LocalDateTime.now(),
                changeDescription = changeDescription,
                responsibleEditor = responsibleEditor
            )
            revisionRepository.createRevision(newRevision)
        }
        return updatedDocument
    }

    private fun getNextRevisionNumber(documentId: String?): Int {
        // Hier könnte eine Abfrage an das RevisionRepository erfolgen, um die höchste Revisionsnummer zu finden
        val latestRevision = revisionRepository.getLatestRevision(documentId)
        return if (latestRevision != null) {
            latestRevision.revisionNumber + 1
        } else {
            1 // Beginnen Sie mit 1, wenn noch keine Revisionen existieren
        }
    }

    fun deleteDocument(id: String): Boolean {
        return documentRepository.deleteDocument(id)
    }
}
