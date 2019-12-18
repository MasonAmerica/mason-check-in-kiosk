package com.bymason.kiosk.checkin.feature.identity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.model.GuestField
import com.bymason.kiosk.checkin.core.ui.StateHolder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class IdentityViewModel(
        private val repository: IdentityRepository,
        private val dispatchers: DispatcherProvider
) : ViewModel() {
    private val _state = StateHolder(State(isLoading = true))
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    init {
        viewModelScope.launch {
            val fields = try {
                repository.getGuestFields()
            } catch (t: Throwable) {
                logBreadcrumb("Failed to get guest fields", t)
                _actions.offer(Action.Navigate(IdentityFragmentDirections.reset()))
                return@launch
            } finally {
                _state.update { copy(isLoading = false) }
            }

            val processedFields = dispatchers.default {
                fields.map { it.copy(hasError = it.field.hasError(null)) }
            }
            _state.update { copy(fieldStates = processedFields) }
        }
    }

    fun onFieldChanged(state: FieldState, newValue: String?, hasFocus: Boolean) {
        viewModelScope.launch {
            processFocusChange(state.field, newValue, hasFocus)
        }
    }

    fun onContinue() {
        if (_state.value.isLoading) return // Prevent repeated clicks

        _state.update { copy(isLoading = true) }
        viewModelScope.launch {
            val sessionId = try {
                repository.registerFields(_state.value.fieldStates)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to register fields", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false) }
            }

            _actions.offer(Action.Navigate(IdentityFragmentDirections.next(sessionId)))
        }
    }

    private suspend fun processFocusChange(
            field: GuestField,
            newValue: String?,
            hasFocus: Boolean
    ) = dispatchers.default {
        val hasError = field.hasError(newValue)
        _state.update {
            val upToDateState = fieldStates.single { it.field.id == field.id }
            val updateIndex = fieldStates.indexOf(upToDateState)
            val newStates = fieldStates.toMutableList().also {
                it[updateIndex] = upToDateState.copy(
                        value = newValue,
                        hasError = hasError,
                        showError = hasError && !hasFocus && upToDateState.hasInteracted,
                        hasInteracted = true
                )
            }
            copy(isContinueButtonEnabled = newStates.none { it.hasError }, fieldStates = newStates)
        }
    }

    private fun GuestField.hasError(newValue: String?) = when {
        regex == null -> false
        newValue == null -> required
        else -> !Pattern.compile(regex).matcher(newValue).matches()
    }

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    data class State(
            val isLoading: Boolean = false,
            val isContinueButtonEnabled: Boolean = false,
            val fieldStates: List<FieldState> = emptyList()
    )

    class Factory(
            private val repository: IdentityRepository,
            private val dispatchers: DispatcherProvider = DefaultDispatcherProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === IdentityViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return IdentityViewModel(repository, dispatchers) as T
        }
    }
}
