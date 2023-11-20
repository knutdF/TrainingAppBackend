package com.trainingsapp.trainigsapp.api

import com.trainingsapp.trainigsapp.model.Document
import com.trainingsapp.trainigsapp.service.DocumentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

fun Route.documentApi(documentService: DocumentService) {
    val logger = LoggerFactory.getLogger("DocumentRouteLogger")

    route("/document") {
        post {
            val receivedJson = call.receiveText()
            logger.info("Received JSON: $receivedJson")
            val document = Json.decodeFromString<Document>(receivedJson)
            val createdDocument = documentService.createDocument(document)
            call.respond(HttpStatusCode.Created, createdDocument)
        }
        }

    get {
        logger.info("Fetching all documents from the database.")
        val allDocuments = documentService.getAllDocuments()
        logger.info("Fetched ${allDocuments.size} documents.")
        call.respond(allDocuments)
    }
    
    get("/{id}") {
        val logger = LoggerFactory.getLogger("GETLogger")
        val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
        val document = documentService.getDocumentById(id)
        logger.debug("Abgerufener Benutzer vor dem Senden der Antwort: {}", document)

        if (document != null) {
            call.respond(document)
        } else {
            call.respond(HttpStatusCode.NotFound, "Document with ID: $id not found")
        }
    }


    put("/{id}") {
        val idFromPath = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
        val documentFromBody = call.receive<Document>()
        val changeDescription = // Retrieve change description from the request
        val responsibleEditor = // Retrieve responsible editor from the request

        val updatedDocument = documentService.updateDocument(documentFromBody, changeDescription, responsibleEditor)
        // Handle the response...
    }

    delete("/{id}") {
        val id = call.parameters["id"] ?: throw IllegalArgumentException("ID is missing")
        documentService.deleteDocument(id)
        call.respond(HttpStatusCode.NoContent)
    }
}

