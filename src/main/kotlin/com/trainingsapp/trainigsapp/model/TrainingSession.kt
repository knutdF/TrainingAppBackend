package com.trainingsapp.trainigsapp.model

data class TrainingSession(
    val id: String,
    val name: String,
    val date: String, // Format: YYYY-MM-DD
    val documents: List<String>, // List of Document ids
    val users: List<String> // List of User ids
)
