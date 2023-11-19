package com.trainingsapp.trainigsapp.service

import com.trainingsapp.trainigsapp.model.Department
import com.trainingsapp.trainigsapp.repository.DepartmentRepository

class DepartmentService(private val departmentRepository: DepartmentRepository) {
    fun createDepartment(department: Department): Any = departmentRepository.createDepartment(department)
    fun getDepartmentById(id: String): Department? = departmentRepository.getDepartmentById(id)
    fun getAllDepartments(): List<Department> = departmentRepository.getAllDepartments()
    fun updateDepartment(department: Department): Department? = departmentRepository.updateDepartment(department)
    fun deleteDepartment(id: String): Boolean = departmentRepository.deleteDepartment(id)
}
