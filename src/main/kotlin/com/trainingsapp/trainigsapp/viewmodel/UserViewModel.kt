package com.trainingsapp.trainigsapp.viewmodel

data class UserViewModel(
    val username: String,
    val userType: String, // Kann "Admin" oder "User" sein
    val departmentName: String? // Kann null sein, wenn der Benutzer keinem Department zugeordnet ist
)
