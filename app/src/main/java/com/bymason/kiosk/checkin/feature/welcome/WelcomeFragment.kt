package com.bymason.kiosk.checkin.feature.welcome

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.flow.collect

class WelcomeFragment(
        auth: FirebaseAuthCompat
) : FragmentBase() {
    private val vm by viewModels<WelcomeViewModel> {
        WelcomeViewModel.Factory(auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    private fun onActionRequested(action: WelcomeViewModel.Action) {
        when (action) {
            is WelcomeViewModel.Action.Navigate ->
                findNavController().navigate(action.directions)
        }
    }
}
