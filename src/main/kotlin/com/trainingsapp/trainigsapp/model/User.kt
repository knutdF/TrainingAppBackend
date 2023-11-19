package com.trainingsapp.trainigsapp.model

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,  // Machen Sie das id-Feld optional
    val email: String?,
    val username: String,
    val password: String,
    val departmentid: String? = null,
    val createdAt: String? = Clock.System.now().toString(),
    val updatedAt: String? = Clock.System.now().toString(),
)


    enum class UserType {
    ADMIN, USER
}
