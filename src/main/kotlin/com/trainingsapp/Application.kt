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

import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.*
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.application.ApplicationStopping
import redis.clients.jedis.Jedis

fun main() {
    embeddedServer(Netty, port = 8081, module = Application::module).start(wait = true)
}
fun Application.module() {
    // ... (Installations- und Konfigurationscode)

    // Redis-Verbindung konfigurieren
    val jedis = Jedis("0.0.0.0", 6451) // Ändere den Host und den Port nach deinen Anforderungen

    // Erstelle Instanzen deiner Repositories, injiziere die Redis-Verbindung
    UserRepository(jedis) // Beispiel: du müsstest eine Implementierung schreiben
    DocumentRepository(jedis) // Beispiel: du müsstest eine Implementierung schreiben
    TrainingSessionRepository(jedis) // Beispiel: du müsstest eine Implementierung schreiben
    DepartmentRepository(jedis) // Beispiel: du müsstest eine Implementierung schreiben

    // ... (der Rest deines bisherigen Codes)

    // Event zum Schließen der Jedis-Verbindung hinzufügen
    environment.monitor.subscribe(ApplicationStopping) {
        jedis.close()
    }
    }


// ... (der Rest deiner Code-Datei, inklusive `main()` Methode)

fun Application.main() {
    // Install Features
    install(ContentNegotiation) {
        jackson {
            // Konfiguriere Jackson, wenn du möchtest
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }

    // Erstelle Instanzen deiner Repositories
    val userRepository = InMemoryUserRepository() // Beispiel: du müsstest eine Implementierung schreiben
    val documentRepository = InMemoryDocumentRepository<Any>() // Beispiel: du müsstest eine Implementierung schreiben
    val trainingSessionRepository = InMemoryTrainingSessionRepository<Any>() // Beispiel: du müsstest eine Implementierung schreiben
    val departmentRepository = InMemoryDepartmentRepository<Any>() // Beispiel: du müsstest eine Implementierung schreiben

    // Erstelle Instanzen deiner Services, injiziere die Repositories
    val userService = UserService(userRepository)
    val documentService = DocumentService(documentRepository)
    val trainingSessionService = TrainingSessionService(trainingSessionRepository)
    val departmentService = DepartmentService(departmentRepository)

    // Definiere die Routen deiner Anwendung
    routing {
        userApi(userService)
        documentApi(documentService)
        trainingSessionApi(trainingSessionService)
        departmentApi(departmentService)

        // Hier könntest du zusätzliche Routen definieren
    }
}

class InMemoryDepartmentRepository<Department> {
    fun createDepartment(department: Department) {

    }

    fun getDepartmentById(id: Department) {

    }

    fun updateDepartment(department: Department) {

    }

    fun deleteDepartment(id: Department) {

    }

}

class InMemoryTrainingSessionRepository<TrainingSession> {
    fun createSession(session: TrainingSession) {

    }

    fun getSessionById(id: TrainingSession) {

    }

    fun updateSession(session: TrainingSession) {

    }

    fun deleteSession(id: TrainingSession) {

    }

}

class InMemoryDocumentRepository<Document> {
    fun createDocument(document: Document) {

    }

    fun getDocumentById(id: Document) {

    }

    fun updateDocument(document: Document) {

    }

    fun deleteDocument(id: Document) {

    }

}

class InMemoryUserRepository {
    fun createUser(user: User) {

    }

    fun getUserById(id: String) {

    }

    fun updateUser(user: User) {

    }

    fun deleteUser(id: String) {

    }

}


