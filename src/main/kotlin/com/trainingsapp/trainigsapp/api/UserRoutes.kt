package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.service.DepartmentService
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.userApi(userService: UserService) {
    route("/user") {
        post {
            // Implementierung von User-Erstellung
        }

        get("/{id}") {
            // Implementierung von Abrufen eines Users durch ID
        }

        put {
            // Implementierung von User-Aktualisierung
        }

        delete("/{id}") {
            // Implementierung von Löschen eines Users durch ID
        }
    }
}
