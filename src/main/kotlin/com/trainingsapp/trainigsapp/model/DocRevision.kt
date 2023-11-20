package com.trainingsapp.trainigsapp.model

import java.time.LocalDateTime

data class DocRevision(
    val revisionId: Int = null,
    val documentId: String,
    val revisionNumber: Unit,
    val revisionDate: LocalDateTime,
    val changeDescription: String,
    val responsibleEditor: String // Ensure this parameter exists
)
