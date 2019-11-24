package com.bymason.kiosk.checkin.feature.employeefinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CompletableDeferred
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class EmployeeFinderViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockEmployeeRepository = mock(EmployeeRepository::class.java)
    private val vm = EmployeeFinderViewModel(mockEmployeeRepository)

    @Test
    fun `Searching for null name noops with empty employees`() = dispatcherRule.runBlocking {
        vm.find(null)

        verify(mockEmployeeRepository, never()).find(any())
        assertThat(vm.employees.value).isEmpty()
    }

    @Test
    fun `Searching for empty name noops with empty employees`() = dispatcherRule.runBlocking {
        vm.find(" ")

        verify(mockEmployeeRepository, never()).find(any())
        assertThat(vm.employees.value).isEmpty()
    }

    @Test
    fun `Searching for valid name lists employees`() = dispatcherRule.runBlocking {
        val employee = Employee("id", "Mr Robot", "email", null)
        `when`(mockEmployeeRepository.find(any())).thenReturn(listOf(employee))

        vm.find("Mr")

        assertThat(vm.employees.value).containsExactly(employee)
    }

    @Test
    fun `Searching for two employees in a row cancels previous search`() = dispatcherRule.runBlocking {
        val employee1 = Employee("id1", "Mr Robot", "email", null)
        val employee2 = Employee("id2", "Mr Robot", "email", null)
        val result1 = CompletableDeferred<List<Employee>>()
        val result2 = CompletableDeferred<List<Employee>>()
        val vm = EmployeeFinderViewModel(object : EmployeeRepository {
            override suspend fun find(name: String) = when (name) {
                "1" -> result1
                "2" -> result2
                else -> error("Unknown $name")
            }.await()
        })

        vm.find("1")
        assertThat(vm.employees.value).isNull()

        vm.find("2")
        result2.complete(listOf(employee2))
        assertThat(vm.employees.value).containsExactly(employee2)

        result1.complete(listOf(employee1))
        assertThat(vm.employees.value).containsExactly(employee2)
    }
}
