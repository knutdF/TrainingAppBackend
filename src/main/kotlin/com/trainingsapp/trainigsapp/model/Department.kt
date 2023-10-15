package com.trainingsapp.trainigsapp.model

data class Department(
    val id: String,
    val name: String,
    val users: List<String>, // List of User ids
    val documents: List<String> // List of Document ids
)
