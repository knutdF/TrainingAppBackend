package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.Department
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.slf4j.LoggerFactory

class DepartmentRepository(private val redis: Jedis) {
    private val logger = LoggerFactory.getLogger(DepartmentRepository::class.java)

    fun createDepartment(department: Department): Department {
        try {
            var departmentId = generateUniqueId()
            while (redis.exists("department:$departmentId")) {
                departmentId = generateUniqueId()
            }

            val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            redis.hset(
                "department:$departmentId", mapOf(
                    "id" to departmentId,
                    "name" to department.name,
                    "description" to (department.description ?: ""),
                    "members" to (department.members?.joinToString(",") ?: ""),
                    "head" to (department.head ?: ""),
                    "createdAt" to now,
                    "updatedAt" to now
                )
            )
            return department.copy(id = departmentId, createdAt = now, updatedAt = now)
        } catch (e: Exception) {
            logger.error("Error creating department", e)
            throw e
        }
    }

    fun updateDepartment(department: Department): Department? {
        try {
            if (redis.exists("department:${department.id}")) {
                val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                redis.hset(
                    "department:${department.id}", mapOf(
                        "name" to department.name,
                        "description" to (department.description ?: ""),
                        "members" to (department.members?.joinToString(",") ?: ""),
                        "head" to (department.head ?: ""),
                        "updatedAt" to now
                    )
                )
                return department.copy(updatedAt = now)
            } else {
                logger.warn("Department with ID ${department.id} does not exist.")
                return null
            }
        } catch (e: Exception) {
            logger.error("Error updating department", e)
            throw e
        }
    }

    fun deleteDepartment(id: String): Boolean {
        try {
            if (redis.exists("department:$id")) {
                redis.del("department:$id")
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            logger.error("Error deleting department", e)
            throw e
        }
    }

    fun getAllDepartments(): List<Department> {
        try {
            val departmentKeys = redis.keys("department:*")
            return departmentKeys.mapNotNull { key ->
                val departmentData = redis.hgetAll(key)
                if (departmentData.isNotEmpty()) {
                    Department(
                        id = departmentData["id"],
                        name = departmentData["name"] ?: "",
                        description = departmentData["description"],
                        members = departmentData["members"]?.split(","),
                        head = departmentData["head"],
                        createdAt = departmentData["createdAt"] ?: "",
                        updatedAt = departmentData["updatedAt"] ?: ""
                    )
                } else {
                    logger.error("Department data for key $key is empty or missing.")
                    null
                }
            }
        } catch (e: Exception) {
            logger.error("Error fetching all departments", e)
            throw e
        }
    }

    fun getDepartmentById(id: String): Department? {
        try {
            val departmentData = redis.hgetAll("department:$id")
            if (departmentData.isNotEmpty()) {
                return Department(
                    id = departmentData["id"],
                    name = departmentData["name"] ?: "",
                    description = departmentData["description"],
                    members = departmentData["members"]?.split(","),
                    head = departmentData["head"],
                    createdAt = departmentData["createdAt"] ?: "",
                    updatedAt = departmentData["updatedAt"] ?: ""
                )
            } else {
                return null
            }
        } catch (e: Exception) {
            logger.error("Error fetching department by ID", e)
            throw e
        }
    }
}
