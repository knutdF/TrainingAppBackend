package com.trainingsapp.trainigsapp.api


import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.client.plugins.logging.* // Import für das Logging-Feature
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
            logger.info("This is an info message")
            logger.error("This is an error message")
            call.respond(HttpStatusCode.OK, user)
            call.respondText("User with ID: $id")

        }

        put {
            val user = call.receive<User>()
            val updatedUser = userService.updateUser(user)
            call.respond(HttpStatusCode.OK, updatedUser)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
            userService.deleteUser(id)
            call.respond(HttpStatusCode.NoContent)
        }


      }

    }


