package com.trainingsapp.trainingsapp

import com.trainingsapp.trainigsapp.api.departmentApi
import com.trainingsapp.trainigsapp.api.documentApi
import com.trainingsapp.trainigsapp.api.trainingSessionApi
import com.trainingsapp.trainigsapp.api.userApi
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

fun Application.module() {
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
    val documentRepository = InMemoryDocumentRepository() // Beispiel: du müsstest eine Implementierung schreiben
    val trainingSessionRepository = InMemoryTrainingSessionRepository() // Beispiel: du müsstest eine Implementierung schreiben
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
    fun createDepartment(department: Department): Department {

    }

    fun getDepartmentById(id: Department): Department? {

    }

    fun updateDepartment(department: Department): Department {

    }

    fun deleteDepartment(id: Department): Boolean {

    }

}

class InMemoryTrainingSessionRepository {

}

class InMemoryDocumentRepository {

}

class InMemoryUserRepository {

}

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}
