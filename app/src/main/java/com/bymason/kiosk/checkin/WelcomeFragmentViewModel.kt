package com.bymason.kiosk.checkin

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.bymason.kiosk.checkin.core.data.Auth
import com.bymason.kiosk.checkin.core.data.SingleLiveEvent

class WelcomeFragmentViewModel(
        private val auth: Auth
) : ViewModel() {
    private val _navEvents = SingleLiveEvent<NavDirections>()
    val navEvents: LiveData<NavDirections> = _navEvents
    private val _intentEvents = SingleLiveEvent<Pair<Int, Intent>>()
    val intentEvents: LiveData<Pair<Int, Intent>> = _intentEvents

    fun start() {
        if (auth.isSignedIn) {
            _navEvents.postValue(WelcomeFragmentDirections.next())
        } else {
            _intentEvents.postValue(SIGN_IN_RC to auth.newSignInIntent())
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
