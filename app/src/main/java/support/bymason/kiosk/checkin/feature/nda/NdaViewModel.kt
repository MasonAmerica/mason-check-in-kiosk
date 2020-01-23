package support.bymason.kiosk.checkin.feature.nda

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.webkit.WebViewClientCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import support.bymason.kiosk.checkin.core.logBreadcrumb
import support.bymason.kiosk.checkin.core.ui.StateHolder

class NdaViewModel(
        private val repository: NdaRepository,
        private val sessionId: String
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }
    private val _viewActions = Channel<ViewAction>(Channel.CONFLATED)
    val viewActions: Flow<ViewAction> = flow { for (e in _viewActions) emit(e) }

    init {
        _actions.offer(Action.SignNda)
    }

    fun createWebViewClient() = object : WebViewClientCompat() {
        override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
        ): Boolean = if (request.url.toString().contains("/redirect/docusign/app")) {
            onNdaSigned(request.url.getQueryParameter("event") ?: "error")
            true
        } else {
            false
        }
    }

    fun createWebChromeClient() = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (_state.value.isWebViewVisible) {
                _state.update { copy(isLoading = newProgress != 100) }
            }
        }
    }

    fun onRestartRequested() {
        _actions.offer(Action.Navigate(NdaFragmentDirections.reset()))
    }

    fun onNdaSigningRequested() {
        _state.update { copy(isLoading = true) }
        viewModelScope.launch {
            try {
                _viewActions.offer(ViewAction.VisitPage(repository.sign(sessionId)))
            } catch (t: Throwable) {
                logBreadcrumb("Failed to get link to sign NDA", t)
                _state.update { copy(isLoading = false) }
                return@launch
            }
        }
    }

    private fun onNdaSigned(result: String) {
        if (result == "signing_complete") {
            finishCheckIn()
        } else {
            _actions.offer(Action.SignNda)
        }
    }

    private fun finishCheckIn() {
        _state.update { copy(isLoading = true, isWebViewVisible = false) }
        viewModelScope.launch {
            try {
                repository.finish(sessionId)
            } catch (t: Throwable) {
                logBreadcrumb("Failed to complete check-in", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false, isWebViewVisible = true) }
            }

            _actions.offer(Action.Navigate(NdaFragmentDirections.next()))
        }
    }

    data class State(
            val isLoading: Boolean = false,
            val isWebViewVisible: Boolean = true
    )

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()

        object SignNda : Action()
    }

    sealed class ViewAction {
        data class VisitPage(val url: String) : ViewAction()
    }

    class Factory(
            private val repository: NdaRepository,
            private val sessionId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === NdaViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return NdaViewModel(repository, sessionId) as T
        }
    }
}
