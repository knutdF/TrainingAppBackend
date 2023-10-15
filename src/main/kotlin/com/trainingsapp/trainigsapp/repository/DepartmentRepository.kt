package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.Department
import io.lettuce.core.api.sync.RedisCommands

class DepartmentRepository(private val redis: RedisCommands<String, String>) {

    fun createDepartment(department: Department) {
        // Beispiel: Serialisieren und speichern Sie das Department-Objekt in Redis
        redis.set("department:${department.id}", department.toString())
    }

    fun getDepartmentById(id: String) {
        // Beispiel: Holen und deserializer Sie das Department-Objekt aus Redis
        val departmentData = redis.get("department:$id")
        // Konvertieren Sie `departmentData` zurück in ein Department-Objekt
        // ...
    }

    // ... Weitere CRUD-Operationen (Update, Delete)
}
