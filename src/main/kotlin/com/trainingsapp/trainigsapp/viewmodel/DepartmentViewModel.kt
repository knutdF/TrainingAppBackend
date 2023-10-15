package com.trainingsapp.trainigsapp.viewmodel

data class DepartmentViewModel(
    val name: String,
    val users: List<UserViewModel>, // Hier werden die tatsächlichen User (nicht nur IDs) gezeigt
    val documents: List<DocumentViewModel> // Hier werden die tatsächlichen Dokumente (nicht nur IDs) gezeigt
)
