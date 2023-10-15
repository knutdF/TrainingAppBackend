package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.service.DepartmentService
import com.trainingsapp.trainigsapp.service.DocumentService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.documentApi(documentService: DocumentService) {
    route("/document") {
        post {
            // Implementierung von Dokument-Erstellung
        }

        get("/{id}") {
            // Implementierung von Abrufen eines Dokuments durch ID
        }

        put {
            // Implementierung von Dokument-Aktualisierung
        }

        delete("/{id}") {
            // Implementierung von Löschen eines Dokuments durch ID
        }
    }
}
