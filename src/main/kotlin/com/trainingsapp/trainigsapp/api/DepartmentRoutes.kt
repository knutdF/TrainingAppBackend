package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.model.Department
import com.trainingsapp.trainigsapp.service.DepartmentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

fun Route.departmentApi(departmentService: DepartmentService) {
    val logger = LoggerFactory.getLogger("DepartmentRouteLogger")

    route("/department") {
        post {
            val receivedJson = call.receiveText()
            logger.info("Received JSON: $receivedJson")
            val department = Json.decodeFromString<Department>(receivedJson)
            val createdDepartment = departmentService.createDepartment(department)
            call.respond(HttpStatusCode.Created, createdDepartment)
        }


        // GET-Abfrage für alle Benutzer
        get {
            logger.info("Fetching all departments from the database.")
            val allDepartments = departmentService.getAllDepartments()
            logger.info("Fetched ${allDepartments.size} departments.")
            call.respond(allDepartments)
        }

        get("/{id}") {
            val logger = LoggerFactory.getLogger("GETLogger")
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            val department = departmentService.getDepartmentById(id)
            logger.debug("Abgerufener Benutzer vor dem Senden der Antwort: {}", department)

            if (department != null) {
                call.respond(department)
            } else {
                call.respond(HttpStatusCode.NotFound, "Department with ID: $id not found")
            }
        }

        put("/{id}") {
            val logger = LoggerFactory.getLogger("UpdateLogger")
            val idFromPath = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            val departmentFromBody = call.receive<Department>()

            // Setzen Sie die ID des Benutzers aus dem Pfad
            val department = departmentFromBody.copy(id = idFromPath)

            val updatedDepartment = departmentService.updateDepartment(department)
            if (updatedDepartment != null) {
                call.respond(updatedDepartment)
            } else {
                call.respond(HttpStatusCode.NotFound, "Department with ID: $idFromPath not found")
            }
        }



        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            departmentService.deleteDepartment(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
