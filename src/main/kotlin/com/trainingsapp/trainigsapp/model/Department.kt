package com.trainingsapp.trainigsapp.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val id: String? = null,  // Machen Sie das id-Feld optional
    val name: String,
    val description: String?,
    val members: List<String>? = null,
    val head: String? = null,
    val createdAt: String,
    val updatedAt: String
)


enum class DepartmentType {
    ADMIN, USER
}


