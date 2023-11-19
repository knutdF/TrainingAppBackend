package com.trainingsapp.trainigsapp.utils

// Utils.kt
object IDGenerator {

    fun generateUniqueId(): String {
        return (10000..99999).random().toString()
    }
}
