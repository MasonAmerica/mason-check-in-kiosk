package com.bymason.kiosk.checkin.feature.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ReportViewModel : ViewModel() {
    private val _actions = Channel<Action>(Channel.CONFLATED)
    val actions: Flow<Action> = flow { for (e in _actions) emit(e) }

    init {
        viewModelScope.launch {
            delay(IDLE_TIMEOUT_MILLIS)
            onContinue()
        }
    }

    fun onContinue() {
        _actions.offer(Action.Navigate(ReportFragmentDirections.reset()))
    }

    sealed class Action {
        data class Navigate(val directions: NavDirections) : Action()
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check(modelClass === ReportViewModel::class.java)

            @Suppress("UNCHECKED_CAST")
            return ReportViewModel() as T
        }
    }

    private companion object {
        const val IDLE_TIMEOUT_MILLIS = 15_000L
    }
}
