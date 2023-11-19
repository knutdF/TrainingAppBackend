package com.trainingsapp.trainigsapp.model

import kotlinx.datetime.Clock

data class Document(
    val documentId: Long,
    val title: String,
    val type: List<String>? = null,
    val departmentid: String? = null,
    val creationDate: String, // Better use a date type like LocalDate in real use
    val lastReviewDate: String, // Also better with a date type
    val responsibleAuthor: String,
    val status: String,
    val createdAt: String? = Clock.System.now().toString(),
    val updatedAt: String? = Clock.System.now().toString(),
)

