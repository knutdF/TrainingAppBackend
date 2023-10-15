package com.trainingsapp.trainigsapp.viewmodel

data class TrainingSessionViewModel(
    val name: String,
    val date: String, // Format: YYYY-MM-DD
    val documents: List<DocumentViewModel>, // Hier werden die tatsächlichen Dokumente (nicht nur IDs) gezeigt
    val users: List<UserViewModel> // Hier werden die tatsächlichen User (nicht nur IDs) gezeigt
)
