package support.bymason.kiosk.checkin.feature.hostfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import support.bymason.kiosk.checkin.core.logBreadcrumb
import support.bymason.kiosk.checkin.core.model.Host
import support.bymason.kiosk.checkin.core.ui.StateHolder

class HostFinderViewModel(
        private val repository: HostRepository,
        private val sessionId: String
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    private var previousSearch: Job? = null

    fun onRestartRequested() {
        _actions.offer(Action.Navigate(HostFinderFragmentDirections.reset()))
    }

    fun onSearch(name: String?) {
        previousSearch?.cancel("New search came in")
        if (name.isNullOrBlank()) {
            _state.update {
                copy(isLoading = false, isSearchHintVisible = true, hosts = emptyList())
            }
            return
        }

        _state.update { copy(isLoading = true, isSearchHintVisible = false) }
        previousSearch = viewModelScope.launch {
            val hosts = try {
                repository.find(name)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to fetch list of hosts", t)
                return@launch
            } finally {
                ensureActive()
                _state.update { copy(isLoading = false) }
            }

            _state.update {
                copy(isSearchHintVisible = hosts.isEmpty(), hosts = hosts)
            }
        }
    }

    fun onFound(host: Host) {
        if (_state.value.isLoading) return // Prevent repeated clicks

        _state.update { copy(isLoading = true) }
        viewModelScope.launch {
            val sessionId = try {
                repository.registerHost(sessionId, host)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to register host", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false) }
            }

            _actions.offer(Action.Navigate(HostFinderFragmentDirections.next(sessionId)))
        }
    }

    data class State(
            val isLoading: Boolean = false,
            val isSearchHintVisible: Boolean = true,
            val hosts: List<Host> = emptyList()
    )

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    class Factory(
            private val repository: HostRepository,
            private val sessionId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === HostFinderViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return HostFinderViewModel(repository, sessionId) as T
        }
    }
}
