package com.trainingsapp.trainigsapp.service


import com.trainingsapp.trainigsapp.model.Document
import com.trainingsapp.trainingsapp.InMemoryDocumentRepository

class DocumentService(private val documentRepository: InMemoryDocumentRepository<Any>) {
    suspend fun createDocument(document: Document): Any? = documentRepository.createDocument(document)
    suspend fun getDocumentById(id: String): Any? = documentRepository.getDocumentById(id)
    suspend fun updateDocument(document: Document): Any? = documentRepository.updateDocument(document)
    suspend fun deleteDocument(id: String): Boolean = documentRepository.deleteDocument(id)
}
