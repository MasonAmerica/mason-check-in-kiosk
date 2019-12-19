package com.bymason.kiosk.checkin

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.flow.collect

class WelcomeFragment(
        auth: FirebaseAuthCompat
) : FragmentBase(R.layout.welcome_fragment) {
    private val vm by viewModels<WelcomeFragmentViewModel> {
        WelcomeFragmentViewModel.Factory(auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            vm.actions.collect { onActionRequested(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP) vm.onTap()
            true
        }
    }

    private fun onActionRequested(action: WelcomeFragmentViewModel.Action) {
        when (action) {
            is WelcomeFragmentViewModel.Action.Navigate ->
                findNavController().navigate(action.directions)
        }
    }
}
