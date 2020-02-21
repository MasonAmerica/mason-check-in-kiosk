package support.bymason.kiosk.checkin.feature.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import com.google.firebase.nongmsauth.errors.FirebaseAuthInvalidEmailException
import com.google.firebase.nongmsauth.errors.FirebaseAuthInvalidPasswordException
import com.google.firebase.nongmsauth.errors.FirebaseAuthUnknownAccountException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import support.bymason.kiosk.checkin.core.logBreadcrumb
import support.bymason.kiosk.checkin.core.ui.StateHolder

class SignInFragmentViewModel(
        private val auth: FirebaseAuthCompat
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    private var email: String? = null
    private var password: String? = null

    fun onEmailChanged(new: String) {
        email = new
        onLoginDetailsChanged()
    }

    fun onPasswordChanged(new: String) {
        password = new
        onLoginDetailsChanged()
    }

    fun onSubmit(email: String, password: String) {
        if (_state.value.isLoading) return // Prevent repeated clicks
        if (!_state.value.isSubmitButtonEnabled) return

        _state.update {
            copy(
                    isLoading = true,
                    isEmailInvalid = false,
                    isAccountUnknown = false,
                    isPasswordInvalid = false
            )
        }
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
            } catch (e: FirebaseAuthInvalidEmailException) {
                _state.update { copy(isEmailInvalid = true) }
                return@launch
            } catch (e: FirebaseAuthUnknownAccountException) {
                _state.update { copy(isAccountUnknown = true) }
                return@launch
            } catch (e: FirebaseAuthInvalidPasswordException) {
                _state.update { copy(isPasswordInvalid = true) }
                return@launch
            } catch (t: Throwable) {
                logBreadcrumb("Failed to log in", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false) }
            }

            _actions.offer(Action.Navigate(SignInFragmentDirections.next()))
        }
    }

    private fun onLoginDetailsChanged() {
        val isLoginValid = !email.isNullOrBlank() && !password.isNullOrBlank()
        _state.update { copy(isSubmitButtonEnabled = isLoginValid) }
    }

    data class State(
            val isLoading: Boolean = false,
            val isSubmitButtonEnabled: Boolean = false,
            val isEmailInvalid: Boolean = false,
            val isAccountUnknown: Boolean = false,
            val isPasswordInvalid: Boolean = false
    )

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    class Factory(
            private val auth: FirebaseAuthCompat
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === SignInFragmentViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return SignInFragmentViewModel(auth) as T
        }
    }
}
