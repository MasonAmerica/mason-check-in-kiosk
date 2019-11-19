package com.bymason.kiosk.checkin

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bymason.kiosk.checkin.core.ui.ActivityBase
import com.bymason.kiosk.checkin.databinding.HomeActivityBinding

class HomeActivity : ActivityBase() {
    private val controller by lazy { findNavController(R.id.content) }
    private val configuration by lazy { AppBarConfiguration(controller.graph) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MasonKiosk_NoActionBar)
        super.onCreate(savedInstanceState)
        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(controller, configuration)
    }

    override fun onSupportNavigateUp() =
            controller.navigateUp(configuration) || super.onSupportNavigateUp()
}
