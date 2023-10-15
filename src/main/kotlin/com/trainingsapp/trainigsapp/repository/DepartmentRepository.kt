package com.trainingsapp.trainigsapp.repository

import com.trainingsapp.trainigsapp.model.Department

interface DepartmentRepository {
    suspend fun createDepartment(department: Department): Department
    suspend fun getDepartmentById(id: String): Department?
    suspend fun updateDepartment(department: Department): Department
    suspend fun deleteDepartment(id: String): Boolean
}
