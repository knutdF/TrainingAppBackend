package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.Department
import com.trainingsapp.trainingsapp.InMemoryDepartmentRepository

class DepartmentService(private val departmentRepository: InMemoryDepartmentRepository<Any>) {
    suspend fun createDepartment(department: Department): Any = departmentRepository.createDepartment(department)
    suspend fun getDepartmentById(id: String): Any? = departmentRepository.getDepartmentById(id)
    suspend fun updateDepartment(department: Department): Any = departmentRepository.updateDepartment(department)
    suspend fun deleteDepartment(id: String): Boolean = departmentRepository.deleteDepartment(id)
}
