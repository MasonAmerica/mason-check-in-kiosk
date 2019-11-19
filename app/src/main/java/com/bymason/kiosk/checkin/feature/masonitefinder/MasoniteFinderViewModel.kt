package com.bymason.kiosk.checkin.feature.masonitefinder

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

class MasoniteFinderViewModel(
        private val repository: EmployeeRepository
) : ViewModel() {
    private val _masonites = MutableLiveData<List<Employee>>()
    val masonites: LiveData<List<Employee>> get() = _masonites

    private var previousSearch: Job? = null

    fun find(masonite: String?) {
        previousSearch?.cancel("New search came in")
        if (masonite.isNullOrBlank()) {
            _masonites.postValue(emptyList())
            return
        }

        previousSearch = viewModelScope.launch {
            val employees = try {
                repository.find(masonite)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of employees", t)
                return@launch
            }

            _masonites.postValue(employees)
        }
    }

    class Factory(
            private val repository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === MasoniteFinderViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return MasoniteFinderViewModel(repository) as T
        }
    }
}
