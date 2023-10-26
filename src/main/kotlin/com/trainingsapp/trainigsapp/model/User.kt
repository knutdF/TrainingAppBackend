package com.trainingsapp.trainigsapp.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String?,
    val username: String,
    val password: String // In Produktionsumgebungen das Passwort niemals im Klartext speichern!){}
) {

}

enum class UserType {
    ADMIN, USER
}
