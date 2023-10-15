package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.Department
import com.trainingsapp.trainigsapp.repository.DepartmentRepository

class DepartmentService(private val departmentRepository: DepartmentRepository) {
    suspend fun createDepartment(department: Department): Department = departmentRepository.createDepartment(department)
    suspend fun getDepartmentById(id: String): Department? = departmentRepository.getDepartmentById(id)
    suspend fun updateDepartment(department: Department): Department = departmentRepository.updateDepartment(department)
    suspend fun deleteDepartment(id: String): Boolean = departmentRepository.deleteDepartment(id)
}
