package com.bymason.kiosk.checkin

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.core.data.Auth
import com.bymason.kiosk.checkin.core.ui.FragmentBase
import kotlinx.coroutines.flow.collect

class WelcomeFragment(
        auth: Auth
) : FragmentBase(R.layout.welcome_fragment) {
    private val vm by viewModels<WelcomeFragmentViewModel> {
        WelcomeFragmentViewModel.Factory(auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            vm.navEvents.collect { findNavController().navigate(it) }
        }
        lifecycleScope.launchWhenCreated {
            vm.intentEvents.collect { (rc, intent) -> startActivityForResult(intent, rc) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP) vm.start()
            true
        }
    }
}
