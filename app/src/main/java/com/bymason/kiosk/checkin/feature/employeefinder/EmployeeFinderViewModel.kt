package com.bymason.kiosk.checkin.feature.employeefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.core.model.Guest
import com.bymason.kiosk.checkin.core.ui.StateHolder
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class EmployeeFinderViewModel(
        private val repository: EmployeeRepository
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }
    private val _viewActions = Channel<ViewAction>(Channel.CONFLATED)
    val viewActions: Flow<ViewAction> = flow { for (e in _viewActions) emit(e) }

    private var previousSearch: Job? = null

    fun find(employee: String?) {
        previousSearch?.cancel("New search came in")
        if (employee.isNullOrBlank()) {
            _state.value = _state.value.copy(
                    isLoading = false,
                    isSearchHintVisible = true,
                    employees = emptyList()
            )
            return
        }

        _state.value = _state.value.copy(isLoading = true)
        previousSearch = viewModelScope.launch {
            val employees = try {
                repository.find(employee)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of employees", t)
                return@launch
            }

            _state.value = _state.value.copy(
                    isLoading = false,
                    isSearchHintVisible = false,
                    employees = employees
            )
        }
    }

    fun onFound(employee: Employee, guest: Guest) {
        _actions.offer(Action.Navigate(EmployeeFinderFragmentDirections.next(employee, guest)))
    }

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    sealed class ViewAction

    data class State(
            val isLoading: Boolean = false,
            val isSearchHintVisible: Boolean = true,
            val employees: List<Employee> = emptyList()
    )

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
