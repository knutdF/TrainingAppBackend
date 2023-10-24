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
import com.trainingsapp.trainigsapp.service.DepartmentService
import com.trainingsapp.trainigsapp.service.DocumentService
import com.trainingsapp.trainigsapp.service.TrainingSessionService
import com.trainingsapp.trainigsapp.service.UserService
import io.ktor.client.plugins.logging.Logging.Companion.install
import io.ktor.server.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.statuspages.*
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis


// ... (andere Importe, doppelte entfernt)

fun Application.module() {
    install(CallLogging) // Korrekter Import für CallLogging

    val logger = LoggerFactory.getLogger("ApplicationLogger")

    val jedis = Jedis("0.0.0.0", 6451)
    logger.info("Connected to Redis at 0.0.0.0:6451")

    UserRepository(jedis)
    DocumentRepository(jedis)
    TrainingSessionRepository(jedis)
    DepartmentRepository(jedis)

    environment.monitor.subscribe(ApplicationStopping) {
        jedis.close()
        logger.info("Closed connection to Redis")
    }

    install(ContentNegotiation) {
        jackson {
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    val userRepository = InMemoryUserRepository()
    val documentRepository = InMemoryDocumentRepository<Any>()
    val trainingSessionRepository = InMemoryTrainingSessionRepository<Any>()
    val departmentRepository = InMemoryDepartmentRepository<Any>()

    val userService = UserService(userRepository)
    val documentService = DocumentService(documentRepository)
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

class InMemoryDepartmentRepository<Department> {
    fun createDepartment(department: Department) {}
    fun getDepartmentById(id: Department) {}
    fun updateDepartment(department: Department) {}
    fun deleteDepartment(id: Department) {}
}

class InMemoryTrainingSessionRepository<TrainingSession> {
    fun createSession(session: TrainingSession) {}
    fun getSessionById(id: TrainingSession) {}
    fun updateSession(session: TrainingSession) {}
    fun deleteSession(id: TrainingSession) {}
}

class InMemoryDocumentRepository<Document> {
    fun createDocument(document: Document) {}
    fun getDocumentById(id: Document) {}
    fun updateDocument(document: Document) {}
    fun deleteDocument(id: Document) {}
}

class InMemoryUserRepository {
    fun createUser(user: User) {}
    fun getUserById(id: String) {}
    fun updateUser(user: User) {}
    fun deleteUser(id: String) {}
}
