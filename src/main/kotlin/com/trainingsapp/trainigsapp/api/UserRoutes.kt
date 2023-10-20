package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.repository.UserRepository
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import redis.clients.jedis.Jedis

fun Route.userApi(userService: UserService) {
    route("/user") {
        post {
            val jedis = Jedis("0.0.0.0", 6451)

            val user = call.receive<User>() // Benutzerdaten aus der Anfrage extrahieren
            userService.createUser(user)
            call.respond(HttpStatusCode.Created) // Antwort mit HTTP 201 Created
        }

        get("/{id}") {
            val userRepository = UserRepository(Jedis("0.0.0.0", 6451))

            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")

            // Daten aus dem UserRepository abrufen
            val user = userRepository.getUserById(id)

            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }


        put {
            val jedis = Jedis("0.0.0.0", 6451)

            val user = call.receive<User>() // Benutzerdaten aus der Anfrage extrahieren

            // Daten in Redis aktualisieren
            jedis.hset(user.id, "name", user.username)
            jedis.hset(user.id, "email", user.email)

            call.respond(HttpStatusCode.NoContent) // Antwort mit HTTP 204 No Content
        }

        delete("/{id}") {
            val jedis = Jedis("0.0.0.0", 6451)

            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")

            // Benutzer aus Redis löschen
            jedis.del(id)

            call.respond(HttpStatusCode.NoContent) // Antwort mit HTTP 204 No Content
        }
    }
}
