package com.bymason.kiosk.checkin

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.core.data.Auth
import com.bymason.kiosk.checkin.core.ui.FragmentBase

class WelcomeFragment(
        auth: Auth
) : FragmentBase(R.layout.welcome_fragment) {
    private val vm by viewModels<WelcomeFragmentViewModel> {
        WelcomeFragmentViewModel.Factory(auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.navEvents.observe(this) {
            findNavController().navigate(it)
        }
        vm.intentEvents.observe(this) { (rc, intent) ->
            startActivityForResult(intent, rc)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP) vm.start()
            true
        }
    }
}
