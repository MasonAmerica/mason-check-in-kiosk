package support.bymason.kiosk.checkin.feature.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WelcomeViewModel(
        auth: FirebaseAuthCompat
) : ViewModel() {
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    init {
        if (auth.uid == null) {
            _actions.offer(Action.Navigate(WelcomeFragmentDirections.signIn()))
        } else {
            _actions.offer(Action.Navigate(WelcomeFragmentDirections.next()))
        }
    }

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    class Factory(
            private val auth: FirebaseAuthCompat
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === WelcomeViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(auth) as T
        }
    }
}
