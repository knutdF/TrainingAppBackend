package com.trainingsapp.trainigsapp.model

data class Document(
    val id: String,
    val name: String,
    val version: Int,
    val contentUrl: String // Kann ein Link oder Pfad zum Upload-Ort sein
)
