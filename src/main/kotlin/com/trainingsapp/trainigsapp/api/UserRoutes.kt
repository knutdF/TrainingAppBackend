package com.trainingsapp.trainigsapp.api


import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
// import io.ktor.client.plugins.logging.* // Import für das Logging-Feature
import org.slf4j.LoggerFactory


fun Route.userApi(userService: UserService) {
    route("/user") {
        post {
            val user = call.receive<User>()
            val createdUser = userService.createUser(user)
            call.respond(HttpStatusCode.Created, createdUser)
        }

        get("/{id}") {
            val logger = LoggerFactory.getLogger("MyLogger")
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            val user = userService.getUserById(id)
            logger.debug("Abgerufener Benutzer vor dem Senden der Antwort: $user")

            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User with ID: $id not found")
            }
        }





        put {
            val user = call.receive<User>()
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")

            val updatedUser = userService.updateUser(user)
            if (user != null) {
                call.respond(user!!)
            } else {
                call.respond(HttpStatusCode.NotFound, "User with ID: $id not found")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            userService.deleteUser(id)
            call.respond(HttpStatusCode.NoContent)
        }


      }

    }


