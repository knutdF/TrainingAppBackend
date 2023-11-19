package com.trainingsapp.trainigsapp.model

import java.util.Date

data class Revision(
    val revisionId: Long,
    val documentId: Long,
    val revisionNumber: Int,
    val revisionDate: Date, // Date type recommended
    val changeDescription: String?,
)
