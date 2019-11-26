package com.bymason.kiosk.checkin.core.data

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

interface Auth {
    val isSignedIn: Boolean

    fun newSignInIntent(): Intent
}

class DefaultAuth(
        private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : Auth {
    override val isSignedIn get() = auth.currentUser != null

    override fun newSignInIntent() = AuthUI.getInstance(auth.app)
            .createSignInIntentBuilder()
            .setAvailableProviders(listOf(
                    AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(false).build()))
            .build()
}
