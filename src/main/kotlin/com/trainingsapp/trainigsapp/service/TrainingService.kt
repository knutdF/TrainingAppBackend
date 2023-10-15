package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.TrainingSession
import com.trainingsapp.trainingsapp.InMemoryTrainingSessionRepository

class TrainingSessionService(private val trainingSessionRepository: InMemoryTrainingSessionRepository<Any>) {
    suspend fun createSession(session: TrainingSession): Any? = trainingSessionRepository.createSession(session)
    suspend fun getSessionById(id: String): Any? = trainingSessionRepository.getSessionById(id)
    suspend fun updateSession(session: TrainingSession): Any? = trainingSessionRepository.updateSession(session)
    suspend fun deleteSession(id: String): Unit = trainingSessionRepository.deleteSession(id)
}
