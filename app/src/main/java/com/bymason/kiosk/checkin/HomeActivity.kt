package com.bymason.kiosk.checkin

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bymason.kiosk.checkin.core.ui.ActivityBase
import com.bymason.kiosk.checkin.databinding.HomeActivityBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

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

        if (FirebaseAuth.getInstance().currentUser == null) {
            handleSignIn(null)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleSignIn(intent)
    }

    private fun handleSignIn(intent: Intent?) {
        val emailOTPSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName(BuildConfig.APPLICATION_ID, true, null)
                .setHandleCodeInApp(true)
                .setUrl("https://mason-check-in-kiosk.firebaseapp.com/")
                .build()
        val providers = listOf(
                EmailBuilder()
                        .enableEmailLinkSignIn()
                        .setActionCodeSettings(emailOTPSettings)
                        .build()
        )

        if (intent == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    SIGN_IN_RC)
        } else {
            val link = intent.dataString
            if (link != null) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setEmailLink(link)
                                .setAvailableProviders(providers)
                                .build(),
                        SIGN_IN_RC)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != SIGN_IN_RC) return

        val response = IdpResponse.fromResultIntent(data)
        if (response != null && FirebaseAuth.getInstance().currentUser == null) {
            handleSignIn(null)
        }
    }

    override fun onSupportNavigateUp() =
            controller.navigateUp(configuration) || super.onSupportNavigateUp()

    private companion object {
        const val SIGN_IN_RC = 876
    }
}
