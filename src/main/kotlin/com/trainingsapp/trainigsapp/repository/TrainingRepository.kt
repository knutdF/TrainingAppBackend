package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.TrainingSession

interface TrainingSessionRepository {
    suspend fun createSession(session: TrainingSession): TrainingSession
    suspend fun getSessionById(id: String): TrainingSession?
    suspend fun updateSession(session: TrainingSession): TrainingSession
    suspend fun deleteSession(id: String): Boolean
}
