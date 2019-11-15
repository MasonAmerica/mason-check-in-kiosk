package com.bymason.kiosk.checkin

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bymason.kiosk.checkin.core.ui.FragmentBase

class WelcomeFragment : FragmentBase(R.layout.welcome_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, e ->
            if (e.action == MotionEvent.ACTION_UP) {
                findNavController().navigate(WelcomeFragmentDirections.next())
            }
            true
        }
    }
}
