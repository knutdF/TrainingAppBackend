package com.trainingsapp.trainigsapp.service


import com.trainingsapp.trainigsapp.model.Document
import com.trainingsapp.trainingsapp.InMemoryDocumentRepository

class DocumentService(private val documentRepository: InMemoryDocumentRepository) {
    suspend fun createDocument(document: Document): Document = documentRepository.createDocument(document)
    suspend fun getDocumentById(id: String): Document? = documentRepository.getDocumentById(id)
    suspend fun updateDocument(document: Document): Document = documentRepository.updateDocument(document)
    suspend fun deleteDocument(id: String): Boolean = documentRepository.deleteDocument(id)
}
