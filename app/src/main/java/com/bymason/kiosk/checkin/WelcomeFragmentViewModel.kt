package com.bymason.kiosk.checkin

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.bymason.kiosk.checkin.core.data.Auth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WelcomeFragmentViewModel(
        private val auth: Auth
) : ViewModel() {
    private val _navEvents = Channel<NavDirections>(Channel.CONFLATED)
    val navEvents: Flow<NavDirections> = flow { for (e in _navEvents) emit(e) }
    private val _intentEvents = Channel<Pair<Int, Intent>>(Channel.CONFLATED)
    val intentEvents: Flow<Pair<Int, Intent>> = flow { for (e in _intentEvents) emit(e) }

    fun start() {
        if (auth.isSignedIn) {
            _navEvents.offer(WelcomeFragmentDirections.next())
        } else {
            _intentEvents.offer(SIGN_IN_RC to auth.newSignInIntent())
        }
    }

    class Factory(
            private val auth: Auth
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === WelcomeFragmentViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return WelcomeFragmentViewModel(auth) as T
        }
    }

    private companion object {
        const val SIGN_IN_RC = 876
    }
}
