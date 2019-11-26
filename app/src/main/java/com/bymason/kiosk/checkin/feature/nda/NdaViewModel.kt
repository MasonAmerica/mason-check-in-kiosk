package com.bymason.kiosk.checkin.feature.nda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.core.model.Guest
import kotlinx.coroutines.launch

class NdaViewModel(
        private val repository: NdaRepository
) : ViewModel() {
    fun finish(employee: Employee, guest: Guest) {
        viewModelScope.launch {
            try {
                repository.finish(employee.id, guest.name, guest.email)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of employees", t)
                return@launch
            }
        }
    }

    class Factory(
            private val repository: NdaRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === NdaViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return NdaViewModel(repository) as T
        }
    }
}
