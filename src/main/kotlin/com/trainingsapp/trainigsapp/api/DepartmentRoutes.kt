package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.service.DepartmentService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.departmentApi(departmentService: DepartmentService) {
    route("/department") {
        post {
            // Implementierung von Abteilungs-Erstellung
        }

        get("/{id}") {
            // Implementierung von Abrufen einer Abteilung durch ID
        }

        put {
            // Implementierung von Abteilungs-Aktualisierung
        }

        delete("/{id}") {
            // Implementierung von Löschen einer Abteilung durch ID
        }
    }
}
