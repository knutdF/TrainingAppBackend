package com.trainingsapp.trainigsapp.model

data class User(
    val id: String,
    val username: String,
    val password: String // In Produktionsumgebungen das Passwort niemals im Klartext speichern!
)

enum class UserType {
    ADMIN, USER
}
