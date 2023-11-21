package com.trainingsapp.trainigsapp.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
@Serializable
data class DocRevision(
    val revisionId: Int? = null,
    val documentId: String,
    val revisionNumber: Int?,
    val revisionDate: LocalDateTime,
    val responsibleAuthor: String? = null // Ensure this parameter exists
)
