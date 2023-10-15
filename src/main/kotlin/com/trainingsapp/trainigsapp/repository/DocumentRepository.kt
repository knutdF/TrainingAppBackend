package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.Document

interface DocumentRepository {
    suspend fun createDocument(document: Document): Document
    suspend fun getDocumentById(id: String): Document?
    suspend fun updateDocument(document: Document): Document
    suspend fun deleteDocument(id: String): Boolean
}
