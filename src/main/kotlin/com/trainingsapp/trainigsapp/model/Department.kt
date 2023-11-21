package com.trainingsapp.trainigsapp.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Department(
    val id: String? = null,  // Machen Sie das id-Feld optional
    val name: String,
    val description: String?,
    val members: List<String>? = null,
    val head: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val documentId: String,
    val title: String,
    val type: String,
    val departmentid: String,
    val creationDate: LocalDateTime,
    val lastReviewDate: LocalDateTime,
    val responsibleAuthor: String,
    val status: String
)


enum class DepartmentType {
    ADMIN, USER
}


