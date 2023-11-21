package com.trainingsapp.trainigsapp.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Document(
    val documentId: String? = null,  // Ensure this is Long, not Unit
    val title: String,
    val type: String? = null,
    val departmentid: String? = null,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val lastReviewDate: LocalDateTime = LocalDateTime.now(),
    val responsibleAuthor: String? = null,
    val status: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
