package com.trainingsapp

import com.trainingsapp.trainigsapp.api.departmentApi
import com.trainingsapp.trainigsapp.api.documentApi
import com.trainingsapp.trainigsapp.api.trainingSessionApi
import com.trainingsapp.trainigsapp.api.userApi
import com.trainingsapp.trainigsapp.model.User
import com.trainingsapp.trainigsapp.repository.DepartmentRepository
import com.trainingsapp.trainigsapp.repository.DocumentRepository
import com.trainingsapp.trainigsapp.repository.TrainingSessionRepository
import com.trainingsapp.trainigsapp.repository.UserRepository
import com.trainingsapp.trainigsapp.repository.RevisionRepository
import com.trainingsapp.trainigsapp.service.DepartmentService
import com.trainingsapp.trainigsapp.service.DocumentService
import com.trainingsapp.trainigsapp.service.TrainingSessionService
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.statuspages.*
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.cors.CORS
import io.ktor.server.plugins.cors.routing.*





    fun Application.module() {
        install(CallLogging) // Korrekter Import für CallLogging

        val logger = LoggerFactory.getLogger("ApplicationLogger")

        val jedis = Jedis("0.0.0.0", 6451)
        logger.info("Connected to Redis at 0.0.0.0:6451")

        UserRepository(jedis)
        DocumentRepository(jedis)
        TrainingSessionRepository(jedis)
        DepartmentRepository(jedis)
        RevisionRepository(jedis)

        environment.monitor.subscribe(ApplicationStopping) {
            jedis.close()
            logger.info("Closed connection to Redis")
        }

        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Patch)
            allowMethod(HttpMethod.Delete)
            allowHeader(HttpHeaders.ContentType)
            allowHeader(HttpHeaders.Authorization)
            allowCredentials = true
            anyHost() // WARNUNG: Dies erlaubt Anfragen von jeder Domain. In einem Produktionsumfeld sollten Sie dies durch die genaue Angabe der erlaubten Domains ersetzen.
        }

        install(ContentNegotiation) {
            json()
        }


        install(StatusPages) {
            exception<Throwable> { call, cause ->
                logger.error("Unhandled exception", cause)
                call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }


        // Initialize repositories
        val userRepository = UserRepository(jedis)
        val documentRepository = DocumentRepository(jedis)
        val trainingSessionRepository = TrainingSessionRepository(jedis)
        val departmentRepository = DepartmentRepository(jedis)
        val revisionRepository = RevisionRepository(jedis)

        // Initialize services
        val userService = UserService(userRepository)
        val documentService = DocumentService(documentRepository) // Only pass DocumentRepository
        val trainingSessionService = TrainingSessionService(trainingSessionRepository)
        val departmentService = DepartmentService(departmentRepository)


        routing {
            userApi(userService)
            documentApi(documentService)
            trainingSessionApi(trainingSessionService)
            departmentApi(departmentService)
        }
    }


    fun main() {
        embeddedServer(Netty, port = 8081, module = Application::module).start(wait = true)
    }


