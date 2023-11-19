package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

fun Route.userApi(userService: UserService) {
    val logger = LoggerFactory.getLogger("UserRouteLogger")

    route("/user") {
        post {
            val receivedJson = call.receiveText()
            logger.info("Received JSON: $receivedJson")
            val user = Json.decodeFromString<User>(receivedJson)
            val createdUser = userService.createUser(user)
            call.respond(HttpStatusCode.Created, createdUser)
        }


        // GET-Abfrage für alle Benutzer
        get {
            logger.info("Fetching all users from the database.")
            val allUsers = userService.getAllUsers()
            logger.info("Fetched ${allUsers.size} users.")
            call.respond(allUsers)
        }

        get("/{id}") {
            val logger = LoggerFactory.getLogger("GETLogger")
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            val user = userService.getUserById(id)
            logger.debug("Abgerufener Benutzer vor dem Senden der Antwort: $user")

            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User with ID: $id not found")
            }
        }

        put("/{id}") {
            val logger = LoggerFactory.getLogger("UpdateLogger")
            val idFromPath = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            val userFromBody = call.receive<User>()

            // Setzen Sie die ID des Benutzers aus dem Pfad
            val user = userFromBody.copy(id = idFromPath)

            val updatedUser = userService.updateUser(user)
            if (updatedUser != null) {
                call.respond(updatedUser)
            } else {
                call.respond(HttpStatusCode.NotFound, "User with ID: $idFromPath not found")
            }
        }



        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            userService.deleteUser(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
