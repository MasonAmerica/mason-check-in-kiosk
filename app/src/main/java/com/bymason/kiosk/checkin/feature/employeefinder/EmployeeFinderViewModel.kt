package com.bymason.kiosk.checkin.feature.employeefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.core.ui.StateHolder
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class EmployeeFinderViewModel(
        private val repository: EmployeeRepository,
        private val sessionId: String
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    private var previousSearch: Job? = null

    fun onSearch(employee: String?) {
        previousSearch?.cancel("New search came in")
        if (employee.isNullOrBlank()) {
            _state.update {
                copy(isLoading = false, isSearchHintVisible = true, employees = emptyList())
            }
            return
        }

        _state.update { copy(isLoading = true) }
        previousSearch = viewModelScope.launch {
            val employees = try {
                repository.find(employee)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of employees", t)
                return@launch
            } finally {
                ensureActive()
                _state.update { copy(isLoading = false) }
            }

            _state.update {
                copy(isSearchHintVisible = employees.isEmpty(), employees = employees)
            }
        }
    }

    fun onFound(employee: Employee) {
        if (_state.value.isLoading) return // Prevent repeated clicks

        _state.update { copy(isLoading = true) }
        viewModelScope.launch {
            val sessionId = try {
                repository.registerEmployee(sessionId, employee)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to register employee", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false) }
            }

            _actions.offer(Action.Navigate(EmployeeFinderFragmentDirections.next(sessionId)))
        }
    }

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    data class State(
            val isLoading: Boolean = false,
            val isSearchHintVisible: Boolean = true,
            val employees: List<Employee> = emptyList()
    )

    class Factory(
            private val repository: EmployeeRepository,
            private val sessionId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === EmployeeFinderViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return EmployeeFinderViewModel(repository, sessionId) as T
        }
    }
}
