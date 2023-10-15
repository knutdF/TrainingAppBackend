package com.trainingsapp.trainigsapp.model

data class User(
    val id: String,
    val username: String,
    val password: String, // In Produktionsumgebungen das Passwort niemals im Klartext speichern!
    val userType: UserType,
    val departmentId: String? // nullable, falls ein User keinem Department zugeordnet ist
)

enum class UserType {
    ADMIN, USER
}
