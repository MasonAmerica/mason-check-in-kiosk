package support.bymason.kiosk.checkin.feature.identity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import support.bymason.kiosk.checkin.core.data.DispatcherProvider
import support.bymason.kiosk.checkin.core.logBreadcrumb
import support.bymason.kiosk.checkin.core.model.GuestField
import support.bymason.kiosk.checkin.core.ui.StateHolder
import java.util.regex.Pattern

class IdentityViewModel(
        private val repository: IdentityRepository,
        private val dispatchers: DispatcherProvider
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    init {
        fetchCompanyMetadata()
        fetchGuestFields()
    }

    fun onRestartRequested() {
        _actions.offer(Action.Navigate(IdentityFragmentDirections.reset()))
    }

    fun onFieldChanged(state: FieldState, newValue: String?, hasFocus: Boolean) {
        viewModelScope.launch {
            processFocusChange(state.field, newValue, hasFocus)
        }
    }

    fun onContinue() {
        if (_state.value.isLoading) return // Prevent repeated clicks

        _state.update { copy(isLoading = true, areViewEnabled = false) }
        viewModelScope.launch {
            val sessionId = try {
                repository.registerFields(_state.value.fieldStates)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to register fields", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false, areViewEnabled = true) }
            }

            _actions.offer(Action.Navigate(IdentityFragmentDirections.next(sessionId)))
        }
    }

    private fun fetchGuestFields() {
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

    private fun fetchCompanyMetadata() {
        viewModelScope.launch {
            val company = try {
                repository.getCompanyMetadata()
            } catch (t: Throwable) {
                logBreadcrumb("Failed to get company metadata", t)
                return@launch
            }

            _state.update { copy(companyLogoUrl = company.logoUrl) }
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

    data class State(
            val isLoading: Boolean = true,
            val areViewEnabled: Boolean = true,
            val isContinueButtonEnabled: Boolean = false,
            val fieldStates: List<FieldState> = emptyList(),
            val companyLogoUrl: String? = null
    )

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    class Factory(
            private val dispatchers: DispatcherProvider,
            private val repository: IdentityRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === IdentityViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return IdentityViewModel(repository, dispatchers) as T
        }
    }
}
