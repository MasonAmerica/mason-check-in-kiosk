package com.bymason.kiosk.checkin.feature.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.MasonKiosk
import com.bymason.kiosk.checkin.core.logBreadcrumb
import com.bymason.kiosk.checkin.core.ui.StateHolder
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WelcomeViewModel(
        private val auth: FirebaseAuthCompat,
        private val repository: WelcomeRepository
) : ViewModel() {
    private val _state = StateHolder(State())
    val state: LiveData<State> get() = _state.liveData
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    init {
        if (auth.uid == null) {
            _actions.offer(Action.Navigate(WelcomeFragmentDirections.signIn()))
        } else {
            fetchCompanyMetadata()
        }
    }

    fun onTap() {
        if (auth.uid == null) {
            _actions.offer(Action.Navigate(WelcomeFragmentDirections.signIn()))
        } else {
            _actions.offer(Action.Navigate(WelcomeFragmentDirections.next()))
        }
    }

    private fun fetchCompanyMetadata() {
        _state.update { copy(isLoading = true) }
        viewModelScope.launch {
            val company = try {
                repository.getCompanyMetadata()
            } catch (t: Throwable) {
                logBreadcrumb("Failed to get company metadata", t)
                return@launch
            } finally {
                _state.update { copy(isLoading = false) }
            }

            val welcomeText = MasonKiosk.getString(
                    R.string.kiosk_checkin_welcome_info_company_title, company.name)
            _state.update {
                copy(welcomeText = welcomeText, companyLogoUrl = company.logoUrl)
            }
        }
    }

    data class State(
            val isLoading: Boolean = false,
            val welcomeText: String? =
                    MasonKiosk.getString(R.string.kiosk_checkin_welcome_info_plain_title),
            val companyLogoUrl: String? = null
    )

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    class Factory(
            private val auth: FirebaseAuthCompat,
            private val repository: WelcomeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === WelcomeViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(auth, repository) as T
        }
    }
}
