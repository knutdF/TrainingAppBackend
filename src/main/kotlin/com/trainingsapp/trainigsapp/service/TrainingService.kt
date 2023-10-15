package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.TrainingSession
import com.trainingsapp.trainingsapp.InMemoryTrainingSessionRepository

class TrainingSessionService(private val trainingSessionRepository: InMemoryTrainingSessionRepository) {
    suspend fun createSession(session: TrainingSession): TrainingSession = trainingSessionRepository.createSession(session)
    suspend fun getSessionById(id: String): TrainingSession? = trainingSessionRepository.getSessionById(id)
    suspend fun updateSession(session: TrainingSession): TrainingSession = trainingSessionRepository.updateSession(session)
    suspend fun deleteSession(id: String): Boolean = trainingSessionRepository.deleteSession(id)
}
