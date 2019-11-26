package com.bymason.kiosk.checkin.feature.employeefinder

import com.bymason.kiosk.checkin.core.model.Employee
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await

interface EmployeeRepository {
    suspend fun find(name: String): List<Employee>
}

class DefaultEmployeeRepository : EmployeeRepository {
    override suspend fun find(name: String): List<Employee> = Default {
        val result = Firebase.functions.getHttpsCallable("findEmployees").call(name).await()
        @Suppress("UNCHECKED_CAST") val data = result.data as List<Map<String, String>>
        data.map { Employee(it.getValue("id"), it.getValue("name"), it.getValue("photoUrl")) }
    }
}
