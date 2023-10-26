package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.Department
import com.trainingsapp.trainigsapp.repository.DepartmentRepository

class DepartmentService(private val departmentRepository: DepartmentRepository) {
    suspend fun createDepartment(department: Department): Any = departmentRepository.createDepartment(department)
    suspend fun getDepartmentById(id: String): Any = departmentRepository.getDepartmentById(id)
    suspend fun updateDepartment(department: Department): Any = departmentRepository.updateDepartment(department)
    suspend fun deleteDepartment(id: String): Unit = departmentRepository.deleteDepartment(id)
}
