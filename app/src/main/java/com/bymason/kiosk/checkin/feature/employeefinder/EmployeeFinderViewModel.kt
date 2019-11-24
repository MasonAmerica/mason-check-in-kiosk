package com.bymason.kiosk.checkin.feature.employeefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.model.Employee
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EmployeeFinderViewModel(
        private val repository: EmployeeRepository
) : ViewModel() {
    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> get() = _employees

    private var previousSearch: Job? = null

    fun find(employee: String?) {
        previousSearch?.cancel("New search came in")
        if (employee.isNullOrBlank()) {
            _employees.postValue(emptyList())
            return
        }

        previousSearch = viewModelScope.launch {
            val employees = try {
                repository.find(employee)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of employees", t)
                return@launch
            }

            _employees.postValue(employees)
        }
    }

    class Factory(
            private val repository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === EmployeeFinderViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return EmployeeFinderViewModel(repository) as T
        }
    }
}
