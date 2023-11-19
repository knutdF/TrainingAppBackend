package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.User
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.slf4j.LoggerFactory

fun generateUniqueId(): String {
    return (10000..99999).random().toString()
}

class UserRepository(private val redis: Jedis) {
    private val logger = LoggerFactory.getLogger(UserRepository::class.java)

    fun createUser(user: User): User {
        try {
            var userId = generateUniqueId()
            while (redis.exists("user:$userId")) {
                userId = generateUniqueId()
            }

            val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val hashedPassword = user.password // TODO: Hash and salt the password before saving

            redis.hset(
                "user:$userId", mapOf(
                    "id" to userId,
                    "username" to user.username,
                    "email" to user.email,
                    "password" to hashedPassword,
                    "departmentid" to user.departmentid,
                    "createdAt" to now, // Setzen Sie das aktuelle Datum und Uhrzeit beim Erstellen
                    "updatedAt" to now  // Setzen Sie das aktuelle Datum und Uhrzeit beim Erstellen
                )
            )
            return user.copy(id = userId, createdAt = now, updatedAt = now)
        } catch (e: Exception) {
            logger.error("Error creating user", e)
            throw e
        }
    }

    fun updateUser(user: User): User? {
        try {
            if (redis.exists("user:${user.id}")) {
                val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                redis.hset(
                    "user:${user.id}", mapOf(
                        "id" to user.id,
                        "username" to user.username,
                        "email" to user.email,
                        "departmentid" to user.departmentid,
                        "createdAt" to user.createdAt, // Behalten Sie das ursprüngliche Erstellungsdatum bei
                        "updatedAt" to now  // Aktualisieren Sie das Datum und Uhrzeit der letzten Änderung
                    )
                )
                return user.copy(updatedAt = now)
            } else {
                logger.warn("User with ID ${user.id} does not exist.")
                return null
            }
        } catch (e: Exception) {
            logger.error("Error updating user", e)
            throw e
        }
    }


    fun deleteUser(id: String): Unit {
        try {
            if (redis.exists("user:$id")) {
                redis.del("user:$id")
            }
        } catch (e: Exception) {
            logger.error("Error deleting user", e)
            throw e
        }
    }

    fun getAllUsers(): List<Map<String, String>> {
        try {
            val userKeys = redis.keys("user:*")
            return userKeys.mapNotNull { key ->
                val userData = redis.hgetAll(key)
                logger.debug("Raw data for user key $key: $userData")
                userData
            }
        } catch (e: Exception) {
            logger.error("Error fetching all users", e)
            throw e
        }
    }

    fun getUserById(id: String): User? {
        try {
            val userData = redis.hgetAll("user:$id")
            val userId = userData["id"]
            val userName = userData["username"]
            val userEmail = userData["email"]
            val userPassword = userData["password"]
            val userDepartmentid = userData["departmentid"]
            val userCreatedAt = userData["createdAt"]
            val userUpdatedAt = userData["updatedAt"]

            if (userId != null && userName != null && userEmail != null) {
                return userPassword?.let {
                    User(
                        id = userId,
                        username = userName,
                        email = userEmail,
                        password = it,
                        departmentid = userDepartmentid,
                        createdAt = userCreatedAt,
                        updatedAt = userUpdatedAt
                    )
                }
            }
            return null
        } catch (e: Exception) {
            logger.error("Error fetching user by ID", e)
            throw e
        }
    }
}
