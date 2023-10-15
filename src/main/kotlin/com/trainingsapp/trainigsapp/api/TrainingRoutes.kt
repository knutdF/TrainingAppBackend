package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.service.DepartmentService
import com.trainingsapp.trainigsapp.service.TrainingSessionService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.trainingSessionApi(trainingSessionService: TrainingSessionService) {
    route("/trainingSession") {
        post {
            // Implementierung von Erstellung einer Trainingssession
        }

        get("/{id}") {
            // Implementierung von Abrufen einer Trainingssession durch ID
        }

        put {
            // Implementierung von Aktualisierung einer Trainingssession
        }

        delete("/{id}") {
            // Implementierung von Löschen einer Trainingssession durch ID
        }
    }
}
